package com.challenge.reactive.processor.commands;

import com.challenge.reactive.gameofthree.exception.GameException;
import com.challenge.reactive.gameofthree.game.Game;
import com.challenge.reactive.gameofthree.game.IGameService;
import com.challenge.reactive.gameofthree.game.domain.InputNumber;
import com.challenge.reactive.gameofthree.game.domain.PlayerAggregate;
import com.challenge.reactive.gameofthree.gameround.domain.GameRoundResult;
import com.challenge.reactive.gameofthree.model.Human;
import com.challenge.reactive.gameofthree.model.IPlayer;
import com.challenge.reactive.model.Action;
import com.challenge.reactive.processor.exceptionhandler.GameExceptionHandler;
import com.challenge.reactive.processor.service.ICommandGameLogService;
import org.springframework.stereotype.Component;

@Component
public class Play extends ChainableCommand<Action> {

    private IGameService gameService;
    private ICommandGameLogService socketChannel;

    /**
     * Play command.
     *
     * @param gameService service to interact with running game.
     * @param socketChannel socket adapter.
     */
    public Play(IGameService gameService, ICommandGameLogService socketChannel) {
        this.gameService = gameService;
        this.socketChannel = socketChannel;
    }

    /**
     * Execute play command.
     *
     * @param action the action to process.
     */
    @Override
    public void execute(Action action) {
        IPlayer authorizedPlayer = new Human(Thread.currentThread().getName(), "");  //inject authorized user
        InputNumber parsedRawInputNumber = parseRawInputNumber(action.getParameter());

        Game gameBeforePlay = gameService.getGame();
        PlayerAggregate playersBeforePlay = gameBeforePlay.getPlayerAggregate();

        try {
            gameService.play(parsedRawInputNumber, authorizedPlayer);
        } catch (GameException ex) {
            new GameExceptionHandler(socketChannel).handle(ex, authorizedPlayer);
            return;
        }

        Game gameAfterPlay = gameService.getGame();
        String message = buildFinalMessage(playersBeforePlay.getRootPlayer(), gameAfterPlay, action.getParameter());

        socketChannel.broadcast(message);

        GameRoundResult gameRoundResultAfterPlay = gameAfterPlay.getGameRoundResult();
        doNext(action);
    }

    private String buildFinalMessage(IPlayer playingCurrentPlayer, Game gameAfterPlay, String inputNumber) {
        GameRoundResult playingRoundResult = gameAfterPlay.getGameRoundResult();

        return playingCurrentPlayer +
                " played number " +
                inputNumber +
                ". The result is " +
                playingRoundResult;
    }

    private InputNumber parseRawInputNumber(String rawInputNumber) {
        try {
            return new InputNumber(Integer.parseInt(rawInputNumber));
        } catch (NumberFormatException ex) {
            socketChannel.send("ERROR: "+ex.getMessage());
        }
        return null;
    }
}