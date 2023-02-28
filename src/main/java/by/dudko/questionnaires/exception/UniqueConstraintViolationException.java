package by.dudko.questionnaires.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class UniqueConstraintViolationException extends RuntimeException {
    private final String fieldName;
    private final String fieldValue;

    @Override
    public String getMessage() {
        return String.format("Value: [%s] for field with name: [%s] already exists.", fieldValue, fieldName);
    }
}
