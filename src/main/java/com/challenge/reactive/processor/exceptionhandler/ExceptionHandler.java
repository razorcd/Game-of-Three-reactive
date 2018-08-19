package com.challenge.reactive.processor.exceptionhandler;

import com.challenge.reactive.gameofthree.model.IPlayer;

public interface ExceptionHandler<E extends RuntimeException> {

    /**
     * Handle exceptions for current player.
     *
     * @param ex the exception to handle
     * @param currentPlayer the current player
     */
    void handle(E ex, IPlayer currentPlayer);
}
