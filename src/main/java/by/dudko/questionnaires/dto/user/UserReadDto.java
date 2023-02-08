package by.dudko.questionnaires.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {
    long id;
    String email;
    String phoneNumber;
    String firstName;
    String lastName;
}
