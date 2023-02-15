package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.dto.user.UserDetailsImpl;
import by.dudko.questionnaires.dto.user.UserEditDto;
import by.dudko.questionnaires.dto.user.UserReadDto;
import by.dudko.questionnaires.mapper.impl.user.UserCreateMapper;
import by.dudko.questionnaires.mapper.impl.user.UserEditMapper;
import by.dudko.questionnaires.mapper.impl.user.UserReadMapper;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.UserService;
import by.dudko.questionnaires.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final JwtUtils jwtUtils;

    @Override
    public PageResponse<UserReadDto> findAll(int page, int size) {
        return PageResponse.of(
                userRepository.findAll(PageRequest.of(page, size))
                        .map(userReadMapper::map)
        );
    }

    @Override
    public AuthenticationResponse login(Credentials credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new AuthenticationResponse(jwtUtils.generateToken(userDetails));
    }

    @Transactional
    @Override
    public UserReadDto save(UserCreateDto createDto) {
        return Optional.of(createDto)
                .map(userCreateMapper::map)
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @Override
    public Optional<UserReadDto> update(long userId, UserEditDto editDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    userEditMapper.map(editDto, user);
                    return userReadMapper.map(user);
                });
    }

    @Transactional
    @Override
    public boolean changePassword(UserChangePasswordDto changePasswordDto) {

        return false;
    }
}
