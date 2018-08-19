package com.challenge.reactive.processor.commands;

import com.challenge.reactive.gameofthree.exception.GameException;
import com.challenge.reactive.gameofthree.game.IGameService;
import com.challenge.reactive.gameofthree.model.Human;
import com.challenge.reactive.model.Action;
import com.challenge.reactive.processor.exceptionhandler.GameExceptionHandler;
import com.challenge.reactive.processor.service.ICommandGameLogService;
import org.springframework.stereotype.Component;

@Component
public class AddHuman extends ChainableCommand<Action> {

    private IGameService gameService;
    private ICommandGameLogService socketChannel;

    /**
     * Add new human player command.
     *
     * @param gameService service to interact with running game.
     * @param socketChannel socket adapter.
     */
    public AddHuman(IGameService gameService, ICommandGameLogService socketChannel) {
        this.gameService = gameService;
        this.socketChannel = socketChannel;
    }

    /**
     * Execute add new human player command.
     *
     * @param action the action to process.
     */
    @Override
    public void execute(Action action) {
        Human authorizedPlayer = new Human(Thread.currentThread().getName(), action.getActionOwner());  //inject authorized user

        try {
            gameService.addPlayer(authorizedPlayer);
        } catch (GameException ex) {
            new GameExceptionHandler(socketChannel).handle(ex, authorizedPlayer);
            return;
        }

        socketChannel.broadcast("Added player " + authorizedPlayer.getName() + " to game.");

        this.doNext(action);
    }
}
