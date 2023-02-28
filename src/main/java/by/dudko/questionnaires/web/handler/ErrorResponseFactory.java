package by.dudko.questionnaires.web.handler;


import by.dudko.questionnaires.dto.error.*;
import by.dudko.questionnaires.exception.UniqueConstraintViolationException;
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
        CustomFieldError[] fieldErrors = bindingResult.getFieldErrors().stream()
                .map(CustomFieldError::of)
                .toArray(CustomFieldError[]::new);
        List<GlobalError> globalErrors = bindingResult.getGlobalErrors().stream()
                .map(GlobalError::of)
                .collect(Collectors.toList());
        addFieldErrors(response, fieldErrors);
        response.addError("globalErrors", globalErrors);
        return response;
    }

    public ErrorResponse of(UniqueConstraintViolationException exception) {
        ErrorResponse response = ErrorResponse.of("Validation failed");
        CustomFieldError fieldError = CustomFieldError.builder()
                .field(exception.getFieldName())
                .message(exception.getMessage())
                .rejectedValue(exception.getFieldValue())
                .build();
        addFieldErrors(response, fieldError);
        return response;
    }

    private void addFieldErrors(ErrorResponse errorResponse, CustomFieldError... fieldErrors) {
        errorResponse.addError("fieldErrors", fieldErrors);
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
