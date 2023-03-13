package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.VerificationDto;
import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.ResetPasswordDto;
import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserDto;

public interface UserService {
    UserDto findById(long userId);

    AuthenticationResponse login(Credentials credentials);

    void signUp(UserDto userDto);

    UserDto update(UserDto editDto);

    boolean changePassword(long userId, UserChangePasswordDto changePasswordDto);

    boolean resetPassword(ResetPasswordDto resetPasswordDto);

    boolean isVerificationCodeValid(VerificationDto verificationDto);

    boolean activateAccount(VerificationDto verificationDto);

    void sendEmailVerificationMessage(String email);

    void sendResetPasswordMessage(String email);
}
