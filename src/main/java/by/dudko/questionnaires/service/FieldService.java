package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.dto.field.FieldReadDto;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FieldService {
    PageResponse<FieldReadDto> findAllByUserId(long userId, Pageable pageable);

    Optional<FieldReadDto> save(long userId, FieldCreateEditDto createEditDto);

    Optional<FieldReadDto> update(long fieldId, FieldCreateEditDto createEditDto);

    boolean deleteById(long fieldId);
}
