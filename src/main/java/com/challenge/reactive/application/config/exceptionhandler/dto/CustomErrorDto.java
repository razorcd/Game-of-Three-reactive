package com.challenge.reactive.application.config.exceptionhandler.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class CustomErrorDto {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;

    private String message;
    private String debugMessage;

    private List<CustomValidationErrorDto> validationErrorDTOs;

    private CustomErrorDto() {
        timestamp = LocalDateTime.now();
    }

    public CustomErrorDto(HttpStatus status) {
        this();
        this.status = status;
    }

    public CustomErrorDto(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public CustomErrorDto(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<CustomValidationErrorDto> getValidationErrorDTOs() {
        return validationErrorDTOs;
    }

    public void setValidationErrorDTOs(List<CustomValidationErrorDto> validationErrorDTOs) {
        this.validationErrorDTOs = validationErrorDTOs;
    }
}