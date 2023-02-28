package by.dudko.questionnaires.mapper.impl;

import by.dudko.questionnaires.dto.ResponseDto;
import by.dudko.questionnaires.mapper.UniversalMapper;
import by.dudko.questionnaires.model.Response;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ResponseMapper implements UniversalMapper<Response, ResponseDto> {
    @Override
    public Supplier<Response> sourceGenerator() {
        return Response::new;
    }

    @Override
    public Supplier<ResponseDto> targetGenerator() {
        return ResponseDto::new;
    }

    @Override
    public ResponseDto map(Response source, ResponseDto target) {
        target.setId(source.getId());
        target.setAnswer(source.getAnswers());
        return target;
    }

    @Override
    public Response reverseMap(ResponseDto source, Response target) {
        target.setAnswers(source.getAnswer());
        return target;
    }
}
