package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.FieldDto;
import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.exception.EntityNotFoundException;
import by.dudko.questionnaires.mapper.impl.FieldMapper;
import by.dudko.questionnaires.model.Field;
import by.dudko.questionnaires.model.FieldType;
import by.dudko.questionnaires.model.Questionnaire;
import by.dudko.questionnaires.repository.FieldRepository;
import by.dudko.questionnaires.repository.FieldTypeRepository;
import by.dudko.questionnaires.repository.QuestionnaireRepository;
import by.dudko.questionnaires.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final FieldTypeRepository fieldTypeRepository;
    private final FieldMapper fieldMapper;

    @Override
    public PageResponse<FieldDto> findAllByQuestionnaireId(long questionnaireId, Pageable pageable) {
        questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> EntityNotFoundException.byId(Questionnaire.class, Long.toString(questionnaireId)));
        return PageResponse.of(fieldRepository.findAllByQuestionnaireIdOrderByOrder(questionnaireId, pageable)
                .map(fieldMapper::map));
    }

    @Override
    public List<String> findAllFieldTypes() {
        return fieldTypeRepository.findAll()
                .stream()
                .map(FieldType::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isFieldOwner(long userId, long fieldId) {
        return fieldRepository.findById(fieldId)
                .map(field -> field.getQuestionnaire().ownerId() == userId)
                .orElseThrow(() -> EntityNotFoundException.byId(Field.class, Long.toString(fieldId)));
    }

    @Transactional
    @Override
    public FieldDto save(long questionnaireId, FieldDto fieldDto) {
        return questionnaireRepository.findById(questionnaireId)
                .map(questionnaire -> {
                    Field field = fieldMapper.reverseMap(fieldDto);
                    field.setType(fieldTypeRepository.findByValue(fieldDto.getType()).get());
                    int order = fieldRepository.findMaxOrderByQuestionnaireId(questionnaireId) + 1;
                    field.setQuestionnaire(questionnaire);
                    field.setOrder(order);
                    return fieldRepository.save(field);
                })
                .map(fieldMapper::map)
                .orElseThrow(() -> EntityNotFoundException.byId(Questionnaire.class, Long.toString(questionnaireId)));
    }

    @Transactional
    @Override
    public FieldDto update(FieldDto fieldDto) {
        long fieldId = fieldDto.getId();
        return fieldRepository.findById(fieldId)
                .map(field -> {
                    field.setType(fieldTypeRepository.findByValue(fieldDto.getType()).get());
                    fieldMapper.reverseMap(fieldDto, field);
                    return fieldDto;
                })
                .orElseThrow(() -> EntityNotFoundException.byId(Field.class, Long.toString(fieldId)));
    }

    @Transactional
    @Override
    public void deleteById(long fieldId) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> EntityNotFoundException.byId(Field.class, Long.toString(fieldId)));
        fieldRepository.delete(field);
    }
}
