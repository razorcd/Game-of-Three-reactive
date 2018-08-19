package com.challenge.reactive.processor.commands;

import com.challenge.reactive.gameofthree.game.IGameService;
import com.challenge.reactive.gameofthree.model.Machine;
import com.challenge.reactive.model.Action;
import com.challenge.reactive.processor.service.ICommandGameLogService;
import org.springframework.stereotype.Component;

@Component
public class AddMachine extends ChainableCommand<Action> {

    private IGameService gameService;
    private ICommandGameLogService socketChannel;

    /**
     * Add new machine player command.
     *
     * @param gameService service to interact with running game.
     * @param socketChannel socket adapter.
     */
    public AddMachine(IGameService gameService, ICommandGameLogService socketChannel) {
        this.gameService = gameService;
        this.socketChannel = socketChannel;
    }

    /**
     * Execute add new machine player command.
     *
     * @param action the action to process.
     */
    @Override
    public void execute(Action action) {
        Machine newPlayer = Machine.generate();

        gameService.addPlayer(newPlayer);
        socketChannel.broadcast("Added AI player " + newPlayer.getName() + " to game.");

        doNext(action);
    }
}
