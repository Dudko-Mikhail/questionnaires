package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.Questionnaire;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final UserRepository userRepository;

    @Override
    public PageResponse<Questionnaire> findAllQuestionnaires(Pageable pageable) {
        return PageResponse.of(userRepository.findAllQuestionnaires(pageable));
    }
}
