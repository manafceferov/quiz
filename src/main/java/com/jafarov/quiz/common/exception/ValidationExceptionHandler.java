package com.jafarov.quiz.common.exception;

import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ValidationExceptionHandler {

    private final MessageSource messageSource;

    public ValidationExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model, Locale locale) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    String fieldLabel = messageSource.getMessage(error.getField(), null, error.getField(), locale);
                    String messageTemplate = error.getDefaultMessage();
                    return messageSource.getMessage(messageTemplate, new Object[]{fieldLabel}, messageTemplate, locale);
                })
                .toList();

        model.addAttribute("errors", errors);

        // Burada redirect ediləcək səhifəni qaytarırsan
        // Əgər səhifəni dinamik qaytarmaq istəyirsənsə, əlavə custom annotation və ya parametr lazım olar
        return "admin/user/create"; // Əgər bu default form səhifəndirsə
    }
}

