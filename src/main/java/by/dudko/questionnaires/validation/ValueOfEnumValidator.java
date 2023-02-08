package by.dudko.questionnaires.validation;

import by.dudko.questionnaires.validation.annotation.ValueOfEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private final List<String> allowedValues = new ArrayList<>();

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        List<String> exclude = Arrays.asList(constraintAnnotation.exclude());
        allowedValues.addAll(Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .filter(value -> !exclude.contains(value))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("must be one of " + allowedValues)
                .addConstraintViolation();
        return allowedValues.contains(value.toString());
    }
}
