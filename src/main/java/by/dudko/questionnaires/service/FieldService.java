package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.dto.field.FieldReadDto;

import java.util.List;
import java.util.Optional;

public interface FieldService {
    List<FieldReadDto> findAllByUserId(long userId);

    Optional<FieldReadDto> save(long userId, FieldCreateEditDto createEditDto);

    Optional<FieldReadDto> update(long fieldId, FieldCreateEditDto createEditDto);

    boolean deleteById(long id);
}
