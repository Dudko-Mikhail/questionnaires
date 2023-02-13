package by.dudko.questionnaires.mapper.impl.field;

import by.dudko.questionnaires.dto.field.FieldReadDto;
import by.dudko.questionnaires.mapper.Mapper;
import by.dudko.questionnaires.model.Field;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FieldReadMapper implements Mapper<Field, FieldReadDto> {
    @Override
    public FieldReadDto map(Field source) {
        return FieldReadDto.builder()
                .id(source.getId())
                .label(source.getLabel())
                .type(source.getType())
                .options(Arrays.asList(source.getOptions()))
                .isRequired(source.isRequired())
                .isActive(source.isActive())
                .build();
    }
}
