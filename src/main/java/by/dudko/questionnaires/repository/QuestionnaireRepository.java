package by.dudko.questionnaires.repository;

import by.dudko.questionnaires.model.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>, JpaSpecificationExecutor<Questionnaire> {
    Page<Questionnaire> findAllByOwnerId(long userId, Pageable pageable);
}
