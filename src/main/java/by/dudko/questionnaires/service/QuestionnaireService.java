package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.Questionnaire;
import org.springframework.data.domain.Pageable;

public interface QuestionnaireService {
    PageResponse<Questionnaire> findAllQuestionnaires(Pageable pageable);
}
