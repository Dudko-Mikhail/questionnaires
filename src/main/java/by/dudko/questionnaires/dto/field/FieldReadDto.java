package by.dudko.questionnaires.dto.field;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FieldReadDto {
    long id;
    String label;
    String type;
    String[] options;
    Boolean isRequired;
    Boolean isActive;
}
