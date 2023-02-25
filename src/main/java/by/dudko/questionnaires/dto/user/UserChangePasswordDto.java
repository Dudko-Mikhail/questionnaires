package by.dudko.questionnaires.dto.user;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
@Builder
public class UserChangePasswordDto {
    @NotEmpty
    String currentPassword;

    @NotEmpty
    String newPassword;
}
