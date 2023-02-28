package by.dudko.questionnaires.mapper.impl;

import by.dudko.questionnaires.dto.FieldDto;
import by.dudko.questionnaires.mapper.UniversalMapper;
import by.dudko.questionnaires.model.Field;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class FieldMapper implements UniversalMapper<Field, FieldDto> {
    @Override
    public Supplier<Field> sourceGenerator() {
        return Field::new;
    }

    @Override
    public Supplier<FieldDto> targetGenerator() {
        return FieldDto::new;
    }

    @Override
    public FieldDto map(Field source, FieldDto target) {
        target.setId(source.getId());
        target.setOrder(source.getOrder());
        target.setLabel(source.getLabel());
        String[] options = source.getOptions();
        if (options != null) {
            target.setOptions(Arrays.stream(options).collect(Collectors.toSet()));
        }
        target.setType(source.getType().getValue());
        target.setIsActive(source.isActive());
        target.setIsRequired(source.isRequired());
        return target;
    }

    @Override
    public Field reversedMap(FieldDto source, Field target) {
        Integer order = source.getOrder();
        if (order != null) {
            target.setOrder(source.getOrder());
        }
        target.setLabel(source.getLabel());
        Set<String> options = source.getOptions();
        target.setOptions(options != null ? options.toArray(new String[0]) : null);
        target.setActive(source.getIsActive());
        target.setRequired(source.getIsRequired());
        return target;
    }
}
