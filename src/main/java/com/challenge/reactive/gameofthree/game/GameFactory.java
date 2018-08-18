package com.challenge.reactive.gameofthree.game;

import com.challenge.reactive.gameofthree.game.domain.PlayerAggregate;
import com.challenge.reactive.gameofthree.gameround.GameRoundService;
import com.challenge.reactive.gameofthree.gameround.IGameRoundService;
import com.challenge.reactive.gameofthree.gameround.domain.GameRoundResult;
import com.challenge.reactive.gameofthree.gameround.gamerules.gameplaylogic.IGameRoundLogic;
import com.challenge.reactive.gameofthree.gameround.gamerules.gamewinlogic.IGameWinLogic;

public class GameFactory {

    private IGameRoundLogic gameLogic;
    private IGameWinLogic winLogic;

    /**
     * Create a new game by defining it's play logic and win logic.
     *
     * @param gameLogic the logic of the game round.
     * @param winLogic the win logic to end game.
     */
    public GameFactory(IGameRoundLogic gameLogic,
                       IGameWinLogic winLogic) {
        this.gameLogic = gameLogic;
        this.winLogic = winLogic;
    }

    /**
     * Build the new game.
     *
     * @return [Game] the new game
     */
    public Game buildNewGame() {
        IGameRoundService gameRoundService = new GameRoundService(gameLogic, winLogic);

        return new Game(gameRoundService, PlayerAggregate.NULL, GameRoundResult.NULL);
    }
}
