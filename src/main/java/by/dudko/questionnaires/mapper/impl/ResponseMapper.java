package by.dudko.questionnaires.mapper.impl;

import by.dudko.questionnaires.dto.ResponseDto;
import by.dudko.questionnaires.mapper.Mapper;
import by.dudko.questionnaires.model.Response;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper implements Mapper<ResponseDto, Response> {
    @Override
    public Response map(ResponseDto source) {
        return Response.builder()
                .id(source.getId() != null ? source.getId() : -1)
                .answers(source.getAnswer())
                .build();
    }
}
