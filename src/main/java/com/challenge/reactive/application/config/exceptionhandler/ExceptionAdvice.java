package com.challenge.reactive.application.config.exceptionhandler;

import com.challenge.reactive.application.config.exception.GameResourceNotFoundException;
import com.challenge.reactive.application.config.exception.GameValidationException;
import com.challenge.reactive.application.config.exceptionhandler.dto.CustomErrorDto;
import com.challenge.reactive.application.config.exceptionhandler.dto.CustomValidationErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * Handle GameResourceNotFoundException exceptions.
     *
     * @param e the GameResourceNotFoundException exception.
     * @return [CustomErrorDto] formatted error dto.
     */
    @ExceptionHandler(GameResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorDto elementNotFound(GameResourceNotFoundException e) {
        LOGGER.warn("Game resource not found exception. Responding with 404 status.", e);
        return new CustomErrorDto(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }

    /**
     * Handle GameValidationException exceptions.
     *
     * @param e the GameValidationException exception.
     * @return [CustomErrorDto] formatted error dto.
     */
    @ExceptionHandler(GameValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorDto validationError(GameValidationException e) {
        LOGGER.warn("Game validation exception. Responding with 400 status.", e);

        CustomErrorDto customErrorDto = new CustomErrorDto(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), e);

        List<CustomValidationErrorDto> customValidationErrorDTOList = e.getExceptionFields().stream()
                .map(exp -> new CustomValidationErrorDto(exp.getObject(), exp.getField(), exp.getValue(), exp.getLocalizedMessage()))
                .collect(Collectors.toList());
        customErrorDto.setValidationErrorDTOs(customValidationErrorDTOList);
        return customErrorDto;
    }

    /**
     * Handle ConstraintViolationException exceptions.
     *
     * @param e the ConstraintViolationException exception.
     * @return [CustomErrorDto] formatted error dto.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorDto constraintValidationException(ConstraintViolationException e) {
        LOGGER.warn("Constraint Violation Exception. Responding with 400 status.", e);
        CustomErrorDto customErrorDto = new CustomErrorDto(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), e);

        List<CustomValidationErrorDto> customValidationErrorDTOList = e.getConstraintViolations().stream()
                .map(exp -> new CustomValidationErrorDto(exp.getRootBeanClass(), exp.getPropertyPath().toString(), exp.getInvalidValue(), exp.getMessage()))
                .collect(Collectors.toList());
        customErrorDto.setValidationErrorDTOs(customValidationErrorDTOList);

        return customErrorDto;
    }
}