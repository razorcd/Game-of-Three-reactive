package com.challenge.reactive.gameofthree.ai;

import com.challenge.reactive.gameofthree.game.domain.InputNumber;
import com.challenge.reactive.gameofthree.game.domain.OutputNumber;

public interface IGameRoundAi {

    /**
     * Calculate the inputNumber of next round based on the last round outputNumber.
     *
     * @param outputNumber the last round outputNumber
     * @return [InputNumber] next calculated input number
     */
    InputNumber calculateNextInputNumberFor(OutputNumber outputNumber);
}
