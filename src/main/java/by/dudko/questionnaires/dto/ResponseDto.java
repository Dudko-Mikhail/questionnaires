package by.dudko.questionnaires.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ResponseDto {
    private Long id;

    @NotEmpty
    private Map<Long, Object> answer;
}
