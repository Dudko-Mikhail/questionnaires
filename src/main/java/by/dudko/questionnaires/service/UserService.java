package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.dto.user.UserEditDto;
import by.dudko.questionnaires.dto.user.UserReadDto;

import java.util.Optional;

public interface UserService {
    PageResponse<UserReadDto> findAll(int page, int size);

    AuthenticationResponse login(Credentials credentials);

    UserReadDto save(UserCreateDto createDto);

    Optional<UserReadDto> update(long userId, UserEditDto editDto);

    boolean changePassword(UserChangePasswordDto changePasswordDto);
}
