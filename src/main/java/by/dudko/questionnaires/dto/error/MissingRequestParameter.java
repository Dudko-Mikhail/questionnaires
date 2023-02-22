package by.dudko.questionnaires.dto.error;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MissingRequestParameter {
    String name;
    String type;
    String message;
}
