package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.questionnaire.QuestionnaireDto;
import by.dudko.questionnaires.dto.questionnaire.QuestionnaireFilter;
import by.dudko.questionnaires.exception.EntityNotFoundException;
import by.dudko.questionnaires.mapper.impl.QuestionnaireMapper;
import by.dudko.questionnaires.model.Questionnaire;
import by.dudko.questionnaires.model.User;
import by.dudko.questionnaires.repository.QuestionnaireRepository;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final UserRepository userRepository;
    private final QuestionnaireMapper questionnaireMapper;

    @Override
    public PageResponse<QuestionnaireDto> findAll(QuestionnaireFilter filter, Pageable pageable) {
        return PageResponse.of(questionnaireRepository.findAll(filter.toSpecification(), pageable)
                .map(questionnaireMapper::map));
    }

    @Override
    public QuestionnaireDto findById(long questionnaireId) {
        return questionnaireRepository.findById(questionnaireId)
                .map(questionnaireMapper::map)
                .orElseThrow(() -> EntityNotFoundException.byId(Questionnaire.class, Long.toString(questionnaireId)));
    }

    @Override
    public PageResponse<QuestionnaireDto> findAllByUserId(long userId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(() -> EntityNotFoundException.byId(User.class, Long.toString(userId)));
        return PageResponse.of(questionnaireRepository.findAllByOwnerId(userId, pageable)
                .map(questionnaireMapper::map));
    }

    @Override
    public boolean isQuestionnaireOwner(long userId, long questionnaireId) {
        return questionnaireRepository.findById(questionnaireId)
                .map(questionnaire -> questionnaire.ownerId() == userId)
                .orElseThrow(() -> EntityNotFoundException.byId(Questionnaire.class, Long.toString(questionnaireId)));
    }

    @Transactional
    @Override
    public QuestionnaireDto save(long userId, QuestionnaireDto questionnaireDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    Questionnaire questionnaire = questionnaireMapper.reverseMap(questionnaireDto);
                    questionnaire.setOwner(user);
                    return questionnaireRepository.saveAndFlush(questionnaire);
                })
                .map(questionnaireMapper::map)
                .orElseThrow(() -> EntityNotFoundException.byId(User.class, Long.toString(userId)));
    }

    @Transactional
    @Override
    public QuestionnaireDto update(long questionnaireId, QuestionnaireDto questionnaireDto) {
        return questionnaireRepository.findById(questionnaireId)
                .map(questionnaire -> questionnaireMapper.reverseMap(questionnaireDto, questionnaire))
                .map(questionnaireMapper::map)
                .orElseThrow(() -> EntityNotFoundException.byId(Questionnaire.class, Long.toString(questionnaireId)));
    }

    @Transactional
    @Override
    public void deleteById(long questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> EntityNotFoundException.byId(Questionnaire.class, Long.toString(questionnaireId)));
        questionnaireRepository.delete(questionnaire);
    }
}
