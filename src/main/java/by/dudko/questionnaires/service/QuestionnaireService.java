package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.questionnaire.QuestionnaireDto;
import by.dudko.questionnaires.dto.questionnaire.QuestionnaireFilter;
import org.springframework.data.domain.Pageable;

public interface QuestionnaireService {
    PageResponse<QuestionnaireDto> findAll(QuestionnaireFilter filter, Pageable pageable);

    QuestionnaireDto findById(long questionnaireId);

    PageResponse<QuestionnaireDto> findAllByUserId(long userId, Pageable pageable);

    boolean isQuestionnaireOwner(long userId, long questionnaireId);

    QuestionnaireDto save(long userId, QuestionnaireDto questionnaireDto);

    QuestionnaireDto update(long questionnaireId, QuestionnaireDto questionnaireDto);

    void deleteById(long questionnaireId);
}
