package by.dudko.questionnaires.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RequiredArgsConstructor(staticName = "of")
public class ErrorResponse {
    private final String message;

    private final Map<String, Object> errors = new HashMap<>();

    public void addError(String description, Object error) {
        errors.put(description, error);
    }
}
