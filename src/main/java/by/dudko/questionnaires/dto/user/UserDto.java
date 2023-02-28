package by.dudko.questionnaires.dto.user;

import by.dudko.questionnaires.model.User;
import by.dudko.questionnaires.validation.action.Create;
import by.dudko.questionnaires.validation.annotation.FieldUniqueness;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@Builder
public class UserDto {
    Long id;

    @Email
    @NotEmpty
    @Length(max = 64)
    @FieldUniqueness(entityClass = User.class, fieldName = "email", groups = Create.class)
    String email;

    @Length(max = 32, min = 3)
    String phoneNumber;

    @Length(max = 32)
    String firstName;

    @Length(max = 32)
    String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(groups = Create.class)
    String password;
}
