package com.challenge.reactive.gameofthree.exception;

/**
 * Exception when game state and domains are invalid.
 */
public class ValidationException extends GameException {

    public ValidationException(String message) {
        super(message);
    }
}
