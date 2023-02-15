package by.dudko.questionnaires.validation;

import by.dudko.questionnaires.validation.annotation.UniqueFieldValue;
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
public class UniqueFieldValueValidator implements ConstraintValidator<UniqueFieldValue, Object> {
    private final EntityManagerFactory entityManagerFactory;
    private String fieldName;
    private Class<?> entityClass;

    @Override
    public void initialize(UniqueFieldValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        Root<?> root = query.from(entityClass);
        query.select(root.get(fieldName));
        List<Object> searchResult = entityManager.createQuery(query).getResultList();
        return !searchResult.contains(value);
    }
}
