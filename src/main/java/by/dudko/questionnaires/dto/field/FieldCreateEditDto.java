package by.dudko.questionnaires.dto.field;

import by.dudko.questionnaires.model.Field;
import by.dudko.questionnaires.validation.annotation.ValueOfEnum;
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
    @NotNull
    Integer order;

    @NotEmpty
    @Length(max = 128)
    String label;

    @NotNull
    @ValueOfEnum(enumClass = Field.Type.class)
    String type;

    Set<String> options;

    @NotNull
    Boolean isRequired;

    @NotNull
    Boolean isActive;
}
