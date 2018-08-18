package com.challenge.reactive.gameofthree.game.validator;

/**
 * Define a class that can be validated with a {@link Validator}.
 *
 * @param <T> the type of the object the validator would validate
 */
public interface CanValidate<T> {

    /**
     * Validate current object with a validator.
     *
     * @param validator the validator to validate with.
     * @return [boolean] if current object is valid for specified validator.
     */
    boolean validate(Validator<T> validator);

    /**
     * Validate current object with a validator.
     *
     * @param validator the validator to validate with.
     * @throws com.challenge.reactive.gameofthree.exception.ValidationException if object is invalid
     */
    void validateOrThrow(Validator<T> validator);
}
