package by.dudko.questionnaires.dto;

import by.dudko.questionnaires.model.FieldType;
import by.dudko.questionnaires.validation.annotation.FieldUniqueness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldDto {
    Long id;

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
