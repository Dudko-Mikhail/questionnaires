package by.dudko.questionnaires.dto.user;

import by.dudko.questionnaires.model.User;
import by.dudko.questionnaires.validation.annotation.UniqueFieldValue;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Value
@Builder
public class UserCreateDto {
    @Email
    @NotEmpty
    @Length(max = 64)
    @UniqueFieldValue(entityClass = User.class, fieldName = "email")
    String email;

    @Length(max = 32, min = 3)
    String phoneNumber;

    @Length(max = 32)
    String firstName;

    @Length(max = 32)
    String lastName;

    @NotEmpty
    String password;
}
