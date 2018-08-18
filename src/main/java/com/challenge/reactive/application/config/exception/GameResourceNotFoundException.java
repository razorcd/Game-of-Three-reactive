package com.challenge.reactive.application.config.exception;

public class GameResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a GameResourceNotFoundException with null
     * as its error message string.
     */
    public GameResourceNotFoundException() {
        super();
    }

    /**
     * Constructs a GameResourceNotFoundException, saving a reference
     * to the error message string `s` for later retrieval by the
     * `getMessage` method.
     *
     * @param s the detail message.
     */
    public GameResourceNotFoundException(String s) {
        super(s);
    }
}
