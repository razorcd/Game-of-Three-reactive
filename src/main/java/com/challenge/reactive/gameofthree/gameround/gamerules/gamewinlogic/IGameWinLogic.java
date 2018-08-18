package com.challenge.reactive.gameofthree.gameround.gamerules.gamewinlogic;

import com.challenge.reactive.gameofthree.game.domain.OutputNumber;

import java.util.function.Function;

@FunctionalInterface
public interface IGameWinLogic extends Function<OutputNumber, Boolean> {
}
