package com.challenge.reactive.gameofthree.config.exception;

public class GameValidationFieldException extends RuntimeException {

    private final Class object;
    private final String field;
    private final Object value;

    /**
     * Constructs a GameValidationFieldException, saving a reference to error object, field, value and error message.
     *
     * @param object the invalid object.
     * @param field the invalid field.
     * @param value the invalid value.
     * @param message the detail message.
     */
    public GameValidationFieldException(Class object,  String field, Object value, String message) {
        super(message);
        this.object = object;
        this.field = field;
        this.value = value;
    }

    public Class getObject() {
        return object;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
