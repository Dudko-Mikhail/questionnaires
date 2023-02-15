package by.dudko.questionnaires.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AuthenticationResponse {
    String token;
}
