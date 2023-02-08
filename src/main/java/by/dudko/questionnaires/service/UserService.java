package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.dto.user.UserEditDto;
import by.dudko.questionnaires.dto.user.UserReadDto;

public interface UserService {
    UserReadDto save(UserCreateDto createDto);

    boolean update(long userId, UserEditDto editDto);

    boolean changePassword(UserChangePasswordDto changePasswordDto);
}
