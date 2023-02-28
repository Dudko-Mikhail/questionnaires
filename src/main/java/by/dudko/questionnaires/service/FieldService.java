package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.dto.field.FieldReadDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FieldService {
    PageResponse<FieldReadDto> findAllByUserId(long userId, Pageable pageable);

    FieldReadDto save(long userId, FieldCreateEditDto createEditDto);

    FieldReadDto update(long fieldId, FieldCreateEditDto createEditDto);

    List<String> findAllFieldTypes();

    void deleteById(long fieldId);
}
