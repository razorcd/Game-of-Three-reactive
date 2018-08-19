package com.challenge.reactive.processor.commands;

import com.challenge.reactive.gameofthree.ai.IGameRoundAi;
import com.challenge.reactive.gameofthree.game.Game;
import com.challenge.reactive.gameofthree.game.IGameService;
import com.challenge.reactive.gameofthree.game.domain.InputNumber;
import com.challenge.reactive.gameofthree.game.domain.OutputNumber;
import com.challenge.reactive.gameofthree.game.domain.PlayerAggregate;
import com.challenge.reactive.gameofthree.gameround.domain.GameRoundResult;
import com.challenge.reactive.gameofthree.model.IPlayer;
import com.challenge.reactive.model.Action;
import com.challenge.reactive.processor.service.ICommandGameLogService;
import org.springframework.stereotype.Component;

@Component
public class PlayMachine extends ChainableCommand<Action> {

    private IGameService gameService;
    private ICommandGameLogService socketChannel;
    private IGameRoundAi gameRoundAi;

    /**
     * Play command for machine if it is next in turn.
     *
     * @param gameService service to interact with running game.
     * @param socketChannel socket adapter.
     * @param gameRoundAi AI algorithm to calculate next game input for machine to play.
     */
    public PlayMachine(IGameService gameService, ICommandGameLogService socketChannel, IGameRoundAi gameRoundAi) {
        this.gameService = gameService;
        this.socketChannel = socketChannel;
        this.gameRoundAi = gameRoundAi;
    }

    /**
     * Execute play command for machine player.
     *
     * @param action the action to process.
     */
    @Override
    public void execute(Action action) {
        playMachineRecursive();

        Game gameAfterPlay = gameService.getGame();
        GameRoundResult gameRoundResultAfterPlay = gameAfterPlay.getGameRoundResult();
        doNext(action);
    }

    private void playMachineRecursive() {
        Game gameBeforePlay = gameService.getGame();
        PlayerAggregate playersBeforePlay = gameBeforePlay.getPlayerAggregate();
        IPlayer nextPlayer = playersBeforePlay.getRootPlayer();
        OutputNumber lastOutputNumber = gameBeforePlay.getGameRoundResult().getOutputNumber();

        if (gameBeforePlay.getGameRoundResult().isWinner()) return;

        if (nextPlayer.isAi()) {
            InputNumber calculatedNumberByAi = gameRoundAi.calculateNextInputNumberFor(lastOutputNumber);

            gameService.play(calculatedNumberByAi, nextPlayer);

            Game gameAfterPlay = gameService.getGame();
            String message2 = buildFinalMessage(nextPlayer, gameAfterPlay, calculatedNumberByAi.toString());

            socketChannel.broadcast(message2);

            playMachineRecursive();
        }
    }

    private String buildFinalMessage(IPlayer playingCurrentPlayer, Game gameAfterPlay, String inputNumber) {
        GameRoundResult playingRoundResult = gameAfterPlay.getGameRoundResult();

        return "\n"+playingCurrentPlayer +
                " played number " +
                inputNumber +
                ". The result is " +
                playingRoundResult;
    }
}