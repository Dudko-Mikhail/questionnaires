package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.ResponseDto;
import by.dudko.questionnaires.exception.EntityNotFoundException;
import by.dudko.questionnaires.mapper.impl.ResponseMapper;
import by.dudko.questionnaires.model.Questionnaire;
import by.dudko.questionnaires.model.Response;
import by.dudko.questionnaires.repository.QuestionnaireRepository;
import by.dudko.questionnaires.repository.ResponseRepository;
import by.dudko.questionnaires.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final QuestionnaireRepository questionnaireRepository;
    private final ResponseRepository responseRepository;
    private final ResponseMapper responseMapper;

    @Override
    public PageResponse<ResponseDto> findAllByQuestionnaireId(long questionnaireId, Pageable pageable) {
        questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> EntityNotFoundException.byId(Questionnaire.class, Long.toString(questionnaireId)));
        return PageResponse.of(responseRepository.findAllByQuestionnaireId(questionnaireId, pageable)
                .map(responseMapper::map));
    }

    @Override
    public ResponseDto save(long questionnaireId, ResponseDto responseDto) {
        return questionnaireRepository.findById(questionnaireId)
                .map(questionnaire -> {
                    Response response = responseMapper.reverseMap(responseDto);
                    response.setQuestionnaire(questionnaire);
                    return response;
                })
                .map(responseRepository::saveAndFlush)
                .map(responseMapper::map)
                .orElseThrow(() -> EntityNotFoundException.byId(Questionnaire.class, Long.toString(questionnaireId)));
    }
}
