package com.challenge.reactive.processor.commands;

import com.challenge.reactive.gameofthree.exception.GameException;
import com.challenge.reactive.gameofthree.game.Game;
import com.challenge.reactive.gameofthree.game.IGameService;
import com.challenge.reactive.gameofthree.game.domain.PlayerAggregate;
import com.challenge.reactive.gameofthree.gameround.domain.GameRoundResult;
import com.challenge.reactive.gameofthree.model.Human;
import com.challenge.reactive.model.Action;
import com.challenge.reactive.processor.exceptionhandler.GameExceptionHandler;
import com.challenge.reactive.processor.service.ICommandGameLogService;
import org.springframework.stereotype.Component;

@Component
public class Start extends ChainableCommand<Action> {

    private IGameService gameService;
    private ICommandGameLogService socketChannel;

    /**
     * Start command.
     *
     * @param gameService service to interact with running game.
     * @param socketChannel socket adapter.
     */
    public Start(IGameService gameService, ICommandGameLogService socketChannel) {
        this.gameService = gameService;
        this.socketChannel = socketChannel;
    }

    /**
     * Execute start command.
     *
     * @param action the action to process.
     */
    @Override
    public void execute(Action action) {
        Human authorizedCurrentPlayer = new Human(Thread.currentThread().getName(), "");
        try {
            gameService.startGame();
        } catch (GameException e) {
            new GameExceptionHandler(socketChannel).handle(e, authorizedCurrentPlayer);
            return;
        }

        Game gameAfterStart = gameService.getGame();
        String finalMessage = buildFinalMessage(gameAfterStart);

        socketChannel.broadcast(finalMessage);

        GameRoundResult gameRoundResultAfterStart = gameAfterStart.getGameRoundResult();
        doNext(action);
    }

    private String buildFinalMessage(Game gameAfterStart) {
        PlayerAggregate playerAfterStart = gameAfterStart.getPlayerAggregate();
        GameRoundResult gameRoundEndResult = gameAfterStart.getGameRoundResult();

        return "Game started. The starting number is " +
                gameRoundEndResult.getOutputNumber() +
                "." +
                " Next to play is " + playerAfterStart.getRootPlayer() +
                ".";
    }
}
