package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.FieldDto;
import by.dudko.questionnaires.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FieldService {
    PageResponse<FieldDto> findAllByUserId(long userId, Pageable pageable);

    FieldDto save(long userId, FieldDto fieldDto);

    FieldDto update(FieldDto fieldDto);

    List<String> findAllFieldTypes();

    void deleteById(long fieldId);
}
