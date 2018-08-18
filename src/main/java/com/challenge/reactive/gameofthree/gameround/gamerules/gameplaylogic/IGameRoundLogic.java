package com.challenge.reactive.gameofthree.gameround.gamerules.gameplaylogic;

import com.challenge.reactive.gameofthree.game.domain.OutputNumber;
import com.challenge.reactive.gameofthree.gameround.domain.GameRoundInput;
import com.challenge.reactive.gameofthree.gameround.gamerules.gameplaylogic.validator.Validatable;

import java.util.function.Function;

/**
 * Interface to implement to define main game round logic.
 */
public interface IGameRoundLogic extends Function<GameRoundInput, OutputNumber>, Validatable<GameRoundInput> {
}
