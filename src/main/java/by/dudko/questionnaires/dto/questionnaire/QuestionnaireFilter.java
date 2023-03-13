package by.dudko.questionnaires.dto.questionnaire;

import by.dudko.questionnaires.model.Questionnaire;
import by.dudko.questionnaires.util.SpecificationBuilder;
import lombok.Value;
import org.springframework.data.jpa.domain.Specification;

@Value
public class QuestionnaireFilter {
    String title;
    String ownerEmail;
    Boolean isActive;

    public Specification<Questionnaire> toSpecification() {
        return SpecificationBuilder.<Questionnaire>build()
                .addSpecification(title,
                        value -> SpecificationBuilder.equalsIgnoreCase(value, root -> root.get("title")))
                .addSpecification(ownerEmail,
                        value -> SpecificationBuilder.equalsIgnoreCase(value, root -> root.get("owner").get("email")))
                .addSpecification(isActive,
                        value -> (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), value))
                .buildAnd();
    }
}
