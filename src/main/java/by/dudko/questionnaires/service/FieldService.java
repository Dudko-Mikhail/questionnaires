package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.FieldDto;
import by.dudko.questionnaires.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FieldService {
    PageResponse<FieldDto> findAllByQuestionnaireId(long questionnaireId, Pageable pageable);

    List<String> findAllFieldTypes();

    boolean isFieldOwner(long userId, long fieldId);

    FieldDto save(long questionnaireId, FieldDto fieldDto);

    FieldDto update(FieldDto fieldDto);

    void deleteById(long fieldId);
}
