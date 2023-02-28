package by.dudko.questionnaires.mapper.impl.field;

import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.mapper.Mapper;
import by.dudko.questionnaires.mapper.MapperWithTargetObject;
import by.dudko.questionnaires.model.Field;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FieldCreateEditMapper implements Mapper<FieldCreateEditDto, Field>,
        MapperWithTargetObject<FieldCreateEditDto, Field> {
    @Override
    public Field map(FieldCreateEditDto source) {
        return copy(source, new Field());
    }

    @Override
    public Field map(FieldCreateEditDto source, Field target) {
        return copy(source, target);
    }

    private Field copy(FieldCreateEditDto source, Field target) {
        if (source.getOrder() != null) {
            target.setOrder(source.getOrder());
        }
        target.setLabel(source.getLabel());
        Set<String> options = source.getOptions();
        target.setOptions(options != null ? options.toArray(new String[0]) : null);
        target.setRequired(source.getIsRequired());
        target.setActive(source.getIsActive());
        return target;
    }
}
