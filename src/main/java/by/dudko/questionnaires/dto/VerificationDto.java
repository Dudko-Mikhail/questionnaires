package by.dudko.questionnaires.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Value
@AllArgsConstructor(staticName = "of")
public class VerificationDto {
    @Email
    @NotEmpty
    @Length(max = 64)
    String email;

    @NotEmpty
    String verificationCode;
}
