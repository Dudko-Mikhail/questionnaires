package by.dudko.questionnaires.web.handler;


import by.dudko.questionnaires.dto.error.CustomFieldError;
import by.dudko.questionnaires.dto.error.ErrorResponse;
import by.dudko.questionnaires.dto.error.GlobalError;
import by.dudko.questionnaires.dto.error.MissingRequestParameter;
import by.dudko.questionnaires.dto.error.TypeMismatchError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ErrorResponseFactory {
    public ErrorResponse of(MethodArgumentNotValidException exception) {
        ErrorResponse response = ErrorResponse.of("Validation failed");
        BindingResult bindingResult = exception.getBindingResult();
        List<CustomFieldError> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(CustomFieldError::of)
                .collect(Collectors.toList());
        List<GlobalError> globalErrors = bindingResult.getGlobalErrors().stream()
                .map(GlobalError::of)
                .collect(Collectors.toList());
        response.addError("fieldErrors", fieldErrors);
        response.addError("globalErrors", globalErrors);
        return response;
    }

    public ErrorResponse of(MissingServletRequestParameterException exception) {
        ErrorResponse response = ErrorResponse.of("Missing request parameter");
        MissingRequestParameter error = MissingRequestParameter.builder()
                .name(exception.getParameterName())
                .type(exception.getParameterType())
                .message(exception.getLocalizedMessage())
                .build();
        response.addError("missingParameter", error);
        return response;
    }

    public ErrorResponse of(TypeMismatchException exception) {
        ErrorResponse response = ErrorResponse.of("Type mismatch error");
        TypeMismatchError error = TypeMismatchError.builder()
                .message(exception.getLocalizedMessage())
                .propertyName(exception.getPropertyName())
                .requiredType(exception.getRequiredType())
                .rejectedValue(exception.getValue())
                .build();
        response.addError("typeMismatch", error);
        return response;
    }
}
