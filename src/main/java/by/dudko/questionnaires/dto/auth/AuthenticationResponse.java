package by.dudko.questionnaires.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class AuthenticationResponse {
    String token;
    long userId;
}
