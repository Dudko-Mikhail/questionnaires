package by.dudko.questionnaires.dto.user;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Value
public class ResetPasswordDto {
    @Email
    @NotEmpty
    @Length(max = 64)
    String email;

    @NotEmpty
    String verificationCode;

    @NotEmpty
    String newPassword;
}
