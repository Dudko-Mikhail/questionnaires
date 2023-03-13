package by.dudko.questionnaires.repository;

import by.dudko.questionnaires.model.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    Page<Response> findAllByQuestionnaireId(long questionnaireId, Pageable pageable);
}
