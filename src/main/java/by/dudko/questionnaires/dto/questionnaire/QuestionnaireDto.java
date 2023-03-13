package by.dudko.questionnaires.dto.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionnaireDto {
    private long id;
    private long userId;
    private String ownerEmail;
    private boolean isActive;

    @NotEmpty
    @Length(max = 255)
    private String title;
    private String description;
    int fieldsCount;
    long responsesCount;
}
