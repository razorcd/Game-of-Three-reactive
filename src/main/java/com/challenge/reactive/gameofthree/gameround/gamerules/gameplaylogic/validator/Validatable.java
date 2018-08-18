package com.challenge.reactive.gameofthree.gameround.gamerules.gameplaylogic.validator;

import com.challenge.reactive.gameofthree.game.validator.Validator;

/**
 * Interface to indicate that the class can be customized with validators.
 * Because this accepts only one object type, it should be used only with classes
 * that have one executable method. Like most functional interfaces.
 *
 * @param <T> type of the input of the
 */
public interface Validatable<T> {

    /**
     * Add validators to current object to customize it's execution.
     *
     * @param validator the validator to add
     */
    void addValidator(Validator<T> validator);
}
