package by.dudko.questionnaires.dto.field;

import by.dudko.questionnaires.model.FieldType;
import by.dudko.questionnaires.validation.annotation.FieldUniqueness;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Value
@Builder
public class FieldCreateEditDto {
    @PositiveOrZero
    Integer order;

    @NotEmpty
    @Length(max = 128)
    String label;

    @NotNull
    @FieldUniqueness(fieldName = "value", entityClass = FieldType.class, isUnique = false)
    String type;

    Set<String> options;

    @NotNull
    Boolean isRequired;

    @NotNull
    Boolean isActive;
}
