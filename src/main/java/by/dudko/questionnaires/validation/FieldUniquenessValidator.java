package by.dudko.questionnaires.validation;

import by.dudko.questionnaires.validation.annotation.FieldUniqueness;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@RequiredArgsConstructor
public class FieldUniquenessValidator implements ConstraintValidator<FieldUniqueness, Object> {
    private final EntityManagerFactory entityManagerFactory;
    private String fieldName;
    private Class<?> entityClass;
    private boolean isUnique;

    @Override
    public void initialize(FieldUniqueness constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.entityClass = constraintAnnotation.entityClass();
        this.isUnique = constraintAnnotation.isUnique();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> query = cb.createQuery();
            Root<?> root = query.from(entityClass);
            query.select(root.get(fieldName));
            query.where(cb.equal(root.get(fieldName), value));
            List<Object> searchResult = entityManager.createQuery(query).getResultList();
            boolean isAbsent = searchResult.isEmpty();
            context.disableDefaultConstraintViolation();
            if (isUnique) {
                context.buildConstraintViolationWithTemplate("Field value is not unique")
                        .addConstraintViolation();
                return isAbsent;
            }
            context.buildConstraintViolationWithTemplate("Invalid field value")
                    .addConstraintViolation();
            return  !isAbsent;
        } finally {
            entityManager.close();
        }
    }
}
