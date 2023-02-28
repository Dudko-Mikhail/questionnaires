package by.dudko.questionnaires.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class EntityNotFoundException extends RuntimeException {
    private final Class<?> entityClass;
    private final String fieldName;
    private final String fieldValue;

    @Override
    public String getMessage() {
        return String.format("Entity from class: [%s] not found by field named: [%s] by value: [%s].",
                entityClass.getSimpleName(), fieldName, fieldValue);
    }
}
