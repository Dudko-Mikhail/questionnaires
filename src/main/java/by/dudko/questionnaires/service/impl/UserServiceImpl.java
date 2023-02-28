package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.VerificationDto;
import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.ResetPasswordDto;
import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserDetailsImpl;
import by.dudko.questionnaires.dto.user.UserDto;
import by.dudko.questionnaires.exception.EntityNotFoundException;
import by.dudko.questionnaires.exception.UniqueConstraintViolationException;
import by.dudko.questionnaires.mapper.impl.UserMapper;
import by.dudko.questionnaires.model.User;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.EmailService;
import by.dudko.questionnaires.service.UserService;
import by.dudko.questionnaires.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Override
    public UserDto findById(long userId) {
        return userRepository.findById(userId)
                .map(userMapper::map)
                .orElseThrow(() -> EntityNotFoundException.of(User.class, User.Fields.id, Long.toString(userId)));
    }

    @Override
    public PageResponse<UserDto> findAll(Pageable pageable) {
        return PageResponse.of(userRepository.findAll(pageable)
                .map(userMapper::map));
    }

    @Override
    public AuthenticationResponse login(Credentials credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return AuthenticationResponse.of(jwtUtils.generateToken(userDetails), userDetails.getId());
    }

    @Transactional
    @Override
    public void signUp(UserDto userDto) {
        User user = Optional.of(userDto)
                .map(userMapper::reverseMap)
                .map(userRepository::saveAndFlush)
                .orElseThrow(NoSuchElementException::new);
        this.sendEmailVerificationMessage(user.getEmail());
    }


    @Transactional
    @Override
    public UserDto update(UserDto userDto) {
        long userId = userDto.getId();
        return userRepository.findById(userId)
                .map(user -> {
                    if (!userRepository.isEmailUniqueExceptUserWithId(userDto.getEmail(), userId)) {
                        throw UniqueConstraintViolationException.of(User.Fields.email, userDto.getEmail());
                    }
                    userMapper.reverseMap(userDto, user);
                    return userDto;
                })
                .orElseThrow(() -> EntityNotFoundException.of(User.class, User.Fields.id, Long.toString(userId)));
    }

    @Transactional
    @Override
    public boolean changePassword(long userId, UserChangePasswordDto changePasswordDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
                        return false;
                    }
                    user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                    emailService.sendPasswordChangedMessage(user.getEmail());
                    return true;
                })
                .orElseThrow(() -> EntityNotFoundException.of(User.class, User.Fields.id, Long.toString(userId)));
    }

    @Transactional
    @Override
    public boolean resetPassword(ResetPasswordDto resetPasswordDto) {
        String email = resetPasswordDto.getEmail();
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (!user.isActivated()) {
                        log.warn("Attempt to reset password in disable account. Email: " + email);
                        throw new DisabledException("User account is disabled");
                    }
                    if (!isVerificationCodeValid(resetPasswordDto.getVerificationCode(), user)) {
                        return false;
                    }
                    user.setVerificationCode(null);
                    user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
                    emailService.sendPasswordChangedMessage(email);
                    return true;
                })
                .orElseThrow(() -> EntityNotFoundException.of(User.class, User.Fields.email, email));
    }

    @Override
    public boolean isVerificationCodeValid(VerificationDto verificationDto) {
        String email = verificationDto.getEmail();
        return userRepository.findByEmail(email)
                .map(user -> isVerificationCodeValid(verificationDto.getVerificationCode(), user))
                .orElseThrow(() -> EntityNotFoundException.of(User.class, User.Fields.email, email));
    }

    @Transactional
    @Override
    public boolean activateAccount(VerificationDto verificationDto) {
        String email = verificationDto.getEmail();
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (!isVerificationCodeValid(verificationDto.getVerificationCode(), user)) {
                        return false;
                    }
                    user.setActivated(true);
                    user.setVerificationCode(null);
                    return true;
                }).orElseThrow(() -> EntityNotFoundException.of(User.class, User.Fields.email, email));
    }

    @Transactional
    @Override
    public void sendEmailVerificationMessage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> EntityNotFoundException.of(User.class, User.Fields.email, email));
        if (user.isActivated()) {
            return;
        }
        UUID verificationCode = UUID.randomUUID();
        user.setVerificationCode(verificationCode);
        emailService.sendEmailVerificationMessage(email, verificationCode.toString());
    }

    @Transactional
    @Override
    public void sendResetPasswordMessage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> EntityNotFoundException.of(User.class, User.Fields.email, email));
        if (!user.isActivated()) {
            return;
        }
        UUID verificationCode = UUID.randomUUID();
        user.setVerificationCode(verificationCode);
        emailService.sendResetPasswordMessage(email, verificationCode.toString());
    }

    private boolean isVerificationCodeValid(String verificationCode, User user) {
        if (user.getVerificationCode() == null) {
            return false;
        }
        String userVerificationCode = user.getVerificationCode().toString();
        return userVerificationCode.equals(verificationCode);
    }
}
