package by.dudko.questionnaires.dto;

import by.dudko.questionnaires.model.Response;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
@AllArgsConstructor(staticName = "of")
public class ResponseDto {
    Long id;

    @NotEmpty
    String answer;

    public static ResponseDto of(Response response) {
        return ResponseDto.of(response.getId(), response.getAnswers());
    }
}
