package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.ResponseDto;
import by.dudko.questionnaires.exception.EntityNotFoundException;
import by.dudko.questionnaires.mapper.impl.ResponseMapper;
import by.dudko.questionnaires.model.Response;
import by.dudko.questionnaires.model.User;
import by.dudko.questionnaires.repository.ResponseRepository;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final UserRepository userRepository;
    private final ResponseRepository responseRepository;
    private final ResponseMapper responseMapper;

    @Override
    public PageResponse<ResponseDto> findAllByUserId(long userId, Pageable pageable) {
        return PageResponse.of(responseRepository.findAllByQuestionnaireOwnerId(userId, pageable)
                .map(ResponseDto::of));
    }

    @Override
    public ResponseDto save(long userId, ResponseDto responseDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    Response response = responseMapper.map(responseDto);
                    response.setQuestionnaireOwner(user);
                    return response;
                })
                .map(responseRepository::saveAndFlush)
                .map(ResponseDto::of)
                .orElseThrow(() -> EntityNotFoundException.of(User.class, "id", Long.toString(userId)));
    }
}
