package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.dto.user.UserEditDto;
import by.dudko.questionnaires.dto.user.UserReadDto;
import by.dudko.questionnaires.model.User;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Optional<UserReadDto> findById(long userId);

    PageResponse<UserReadDto> findAll(Pageable pageable);

    AuthenticationResponse login(Credentials credentials);

    void signUp(UserCreateDto createDto);

    Optional<UserReadDto> update(UserEditDto editDto);

    boolean changePassword(long userId, UserChangePasswordDto changePasswordDto);

    boolean activateAccount(long userId, String verificationCode);

    void sendEmailVerificationMessage(String email);

    String generateVerificationCode(User user);

    boolean isVerificationCodeValid(User user, String verificationCode);
}
