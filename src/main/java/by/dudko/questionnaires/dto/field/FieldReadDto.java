package by.dudko.questionnaires.dto.field;

import by.dudko.questionnaires.model.Field;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FieldReadDto {
    long id;
    String label;
    Field.Type type;
    Boolean isRequired;
    Boolean isActive;
}
