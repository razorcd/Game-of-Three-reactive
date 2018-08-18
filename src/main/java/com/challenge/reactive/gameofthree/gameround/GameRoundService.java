package com.challenge.reactive.gameofthree.gameround;

import com.challenge.reactive.gameofthree.exception.GameRoundException;
import com.challenge.reactive.gameofthree.game.domain.OutputNumber;
import com.challenge.reactive.gameofthree.gameround.domain.GameRoundInput;
import com.challenge.reactive.gameofthree.gameround.domain.GameRoundResult;
import com.challenge.reactive.gameofthree.gameround.gamerules.gameplaylogic.IGameRoundLogic;
import com.challenge.reactive.gameofthree.gameround.gamerules.gamewinlogic.IGameWinLogic;

public class GameRoundService implements IGameRoundService {
    private final IGameRoundLogic gameRoundRule;
    private final IGameWinLogic winLogic;

    /**
     * Initialize game round.
     *
     * @param gameRoundRule the algorithm that will generate the game output of the played round.
     * @param winLogic the logic that determines if played round is a win.
     */
    public GameRoundService(final IGameRoundLogic gameRoundRule, final IGameWinLogic winLogic) {
        this.gameRoundRule = gameRoundRule;
        this.winLogic = winLogic;
    }

    /**
     * Play one round based on an input value.
     *
     * @param gameRoundInput the game round input representing user input and last round output.
     * @return [GameRoundResult] the result of the played round.
     * @throws GameRoundException if invalid input number.
     */
    public GameRoundResult play(final GameRoundInput gameRoundInput) {
        OutputNumber outputNumber = gameRoundRule.apply(gameRoundInput);
        boolean winner = winLogic.apply(outputNumber);

        return new GameRoundResult(outputNumber, winner);
    }
}
