package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.ResponseDto;
import org.springframework.data.domain.Pageable;

public interface ResponseService {
    PageResponse<ResponseDto> findAllByQuestionnaireId(long questionnaireId, Pageable pageable);

    ResponseDto save(long questionnaireId, ResponseDto responseDto);
}
