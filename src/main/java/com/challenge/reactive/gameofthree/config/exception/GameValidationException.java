package com.challenge.reactive.gameofthree.config.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameValidationException extends RuntimeException {

    private final List<GameValidationFieldException> exceptionFields;

    /**
     * Constructs a GameValidationException, saving a reference to error message and no validation field error.
     *
     * @param message the detail message.
     */
    public GameValidationException(String message) {
        super(message);
        this.exceptionFields = new ArrayList<>();
    }

    /**
     * Constructs a GameValidationException, saving a reference to error message and validation error for only one field.
     *
     * @param message the detail message.
     * @param exceptionField the error for one field.
     */
    public GameValidationException(String message, GameValidationFieldException exceptionField) {
        super(message);
        this.exceptionFields = new ArrayList<>(Arrays.asList(exceptionField));
    }

    /**
     * Constructs a GameValidationException, saving a reference to error message and validation error for each field.
     *
     * @param message the detail message.
     * @param exceptionFields the list of error for each field.
     */
    public GameValidationException(String message, List<GameValidationFieldException> exceptionFields) {
        super(message);
        this.exceptionFields = exceptionFields;
    }

    public List<GameValidationFieldException> getExceptionFields() {
        return exceptionFields;
    }
}
