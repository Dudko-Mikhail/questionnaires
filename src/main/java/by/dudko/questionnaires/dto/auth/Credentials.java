package by.dudko.questionnaires.dto.auth;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Value
public class Credentials {
    @Email
    @Length(max = 64)
    @NotEmpty
    String email;

    @NotEmpty
    String password;
}
