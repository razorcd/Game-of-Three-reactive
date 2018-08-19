package com.challenge.reactive.processor.exceptionhandler;

import com.challenge.reactive.gameofthree.exception.GameException;
import com.challenge.reactive.gameofthree.model.IPlayer;
import com.challenge.reactive.processor.service.ICommandLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameExceptionHandler implements ExceptionHandler<GameException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameExceptionHandler.class);
    private ICommandLogService socketChannel;

    public GameExceptionHandler(ICommandLogService socketChannel) {
        this.socketChannel = socketChannel;
    }

    /**
     * Handle GameException by sending error message to player socket.
     *
     * @param ex the exception to handle.
     * @param currentPlayer the authorized current player
     */
    @Override
    public void handle(GameException ex, IPlayer currentPlayer) {
        LOGGER.debug("GameException for player {}. Error message: {}.", currentPlayer, ex.getMessage());
        String errMessage = buildErrorMessage(ex);
//        socketChannel.send(errMessage);
    }

    private String buildErrorMessage(GameException ex) {
        return "ERROR: " +
//                player +
//                ": " +
                ex.getMessage();
    }
}
