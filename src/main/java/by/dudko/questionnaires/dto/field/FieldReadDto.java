package by.dudko.questionnaires.dto.field;

import by.dudko.questionnaires.model.Field;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class FieldReadDto {
    long id;
    String label;
    Field.Type type;
    List<String> options;
    Boolean isRequired;
    Boolean isActive;
}
