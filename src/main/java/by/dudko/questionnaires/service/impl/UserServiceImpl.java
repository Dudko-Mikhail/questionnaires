package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.dto.user.UserDetailsImpl;
import by.dudko.questionnaires.dto.user.UserEditDto;
import by.dudko.questionnaires.dto.user.UserReadDto;
import by.dudko.questionnaires.exception.UserNotFoundException;
import by.dudko.questionnaires.mapper.impl.user.UserCreateMapper;
import by.dudko.questionnaires.mapper.impl.user.UserEditMapper;
import by.dudko.questionnaires.mapper.impl.user.UserReadMapper;
import by.dudko.questionnaires.model.User;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.EmailService;
import by.dudko.questionnaires.service.UserService;
import by.dudko.questionnaires.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;
    private final UserEditMapper userEditMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Override
    public Optional<UserReadDto> findById(long userId) {
        return userRepository.findById(userId)
                .map(userReadMapper::map);
    }

    @Override
    public PageResponse<UserReadDto> findAll(Pageable pageable) {
        return PageResponse.of(userRepository.findAll(pageable)
                .map(userReadMapper::map));
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
    public void signUp(UserCreateDto createDto) {
        User user = Optional.of(createDto)
                .map(userCreateMapper::map)
                .map(userRepository::saveAndFlush)
                .orElseThrow(NoSuchElementException::new);
        emailService.sendEmailVerificationMessage(user.getId(), user.getEmail(), generateVerificationCode(user));
    }

    @Transactional
    @Override
    public Optional<UserReadDto> update(UserEditDto editDto) {
        return userRepository.findById(editDto.getId())
                .map(user -> {
                    userEditMapper.map(editDto, user);
                    return userReadMapper.map(user);
                });
    }

    @Transactional
    @Override
    public boolean changePassword(long userId, UserChangePasswordDto changePasswordDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
                        return false;
                    }
                    user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                    return true;
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    @Override
    public boolean activateAccount(long userId, String verificationCode) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (isVerificationCodeValid(user, verificationCode)) {
                        user.setActivated(true);
                        return true;
                    }
                    return false;
                }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public void sendEmailVerificationMessage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
        if (!user.isActivated()) {
            emailService.sendEmailVerificationMessage(user.getId(), email, generateVerificationCode(user));
        }
    }

    @Override
    public String generateVerificationCode(User user) {
        String rawCode = buildRawCode(user);
        return passwordEncoder.encode(rawCode);
    }

    @Override
    public boolean isVerificationCodeValid(User user, String verificationCode) {
        String rawCode = buildRawCode(user);
        return passwordEncoder.matches(rawCode, verificationCode);
    }

    private String buildRawCode(User user) {
        return String.format("%d:%s:%s:%1$d", user.getId(), user.getEmail(), user.getPassword());
    }
}
