package com.jafarov.quiz.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import java.lang.reflect.Field;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    private final MessageSource messageSource;

    public FieldMatchValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field firstField = value.getClass().getDeclaredField(firstFieldName);
            Field secondField = value.getClass().getDeclaredField(secondFieldName);
            firstField.setAccessible(true);
            secondField.setAccessible(true);
            Object firstValue = firstField.get(value);
            Object secondValue = secondField.get(value);

            if (firstValue == null && secondValue == null) {
                return true;
            }
            if (firstValue != null && firstValue.equals(secondValue)) {
                return true;
            } else {
                String firstFieldLabel = messageSource.getMessage(firstFieldName, null, LocaleContextHolder.getLocale());
                String secondFieldLabel = messageSource.getMessage(secondFieldName, null, LocaleContextHolder.getLocale());

                String errorMessage = messageSource.getMessage("FieldMatch",
                        new Object[]{firstFieldLabel, secondFieldLabel}, LocaleContextHolder.getLocale());

                context.buildConstraintViolationWithTemplate(errorMessage)
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
                return false;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error during field match validation", e);
        }
    }
}