package by.dudko.questionnaires.service;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.ResponseDto;
import org.springframework.data.domain.Pageable;

public interface ResponseService {
    PageResponse<ResponseDto> findAllByUserId(long userId, Pageable pageable);

    ResponseDto save(long userId, ResponseDto responseDto);
}
