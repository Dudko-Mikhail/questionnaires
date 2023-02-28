package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.VerificationDto;
import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.ResetPasswordDto;
import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.dto.user.UserEditDto;
import by.dudko.questionnaires.dto.user.UserReadDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserReadDto findById(long userId);

    PageResponse<UserReadDto> findAll(Pageable pageable);

    AuthenticationResponse login(Credentials credentials);

    void signUp(UserCreateDto createDto);

    UserReadDto update(long userId, UserEditDto editDto);

    boolean changePassword(long userId, UserChangePasswordDto changePasswordDto);

    boolean resetPassword(ResetPasswordDto resetPasswordDto);

    boolean isVerificationCodeValid(VerificationDto verificationDto);

    boolean activateAccount(VerificationDto verificationDto);

    void sendEmailVerificationMessage(String email);

    void sendResetPasswordMessage(String email);
}
