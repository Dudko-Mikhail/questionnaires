package by.dudko.questionnaires.dto.user;

import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Value
@Builder
public class UserEditDto {
    @Email
    @NotEmpty
    @Length(max = 64)
    String email;

    @Length(max = 32, min = 3)
    String phoneNumber;

    @Length(max = 32)
    String firstName;

    @Length(max = 32)
    String lastName;
}
