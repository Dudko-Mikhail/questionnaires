package by.dudko.questionnaires.mapper.impl;

import by.dudko.questionnaires.dto.questionnaire.QuestionnaireDto;
import by.dudko.questionnaires.mapper.UniversalMapper;
import by.dudko.questionnaires.model.Questionnaire;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class QuestionnaireMapper implements UniversalMapper<Questionnaire, QuestionnaireDto> {
    @Override
    public Supplier<Questionnaire> sourceGenerator() {
        return Questionnaire::new;
    }

    @Override
    public Supplier<QuestionnaireDto> targetGenerator() {
        return QuestionnaireDto::new;
    }

    @Override
    public QuestionnaireDto map(Questionnaire source, QuestionnaireDto target) {
        target.setId(source.getId());
        target.setUserId(source.ownerId());
        target.setOwnerEmail(source.ownerEmail());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setFieldsCount(source.getFields().size());
        target.setResponsesCount(source.getResponses().size());
        target.setActive(source.isActive());
        return target;
    }

    @Override
    public Questionnaire reverseMap(QuestionnaireDto source, Questionnaire target) {
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        System.out.println(source.isActive());
        target.setActive(source.isActive());
        return target;
    }
}
