package com.challenge.reactive.gameofthree.gameround.gamerules.gameplaylogic;

import com.challenge.reactive.gameofthree.game.domain.OutputNumber;
import com.challenge.reactive.gameofthree.game.validator.Validator;
import com.challenge.reactive.gameofthree.gameround.domain.GameRoundInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulated game logic that divides input number by {@link #DIVIDER}.
 */
@Component
public class DivideByThreeLogic implements IGameRoundLogic {

    /**
     * The divider value.
     */
    private static final int DIVIDER = 3;

    private final List<Validator<GameRoundInput>> validators;

    /**
     * Create new divide by three game logic.
     */
    public DivideByThreeLogic() {
        this.validators = new ArrayList<>();
    }

    /**
     * Apply game logic to game round input and generate output number.
     *
     * @param gameRoundInput the game round input representing user input and last round output.
     * @return [OutputNumber] the resulted output number after applying current game logic.
     */
    @Override
    public OutputNumber apply(final GameRoundInput gameRoundInput) {
        validateOrThrow(gameRoundInput);
        return applyLogic(gameRoundInput);
    }

    /**
     * Add validator to customize current algorithm.
     *
     * @param validator the game round input validator.
     */
    @Autowired(required = false)
    public void addValidator(Validator<GameRoundInput> validator) {
        validators.add(validator);
    }

    /**
     * Validates game round input with any defined validators.
     *
     * @param gameRoundInput game round input to validate to validate.
     */
    private void validateOrThrow(GameRoundInput gameRoundInput) {
        validators.forEach(validator -> validator.validateOrThrow(gameRoundInput));
    }

    private OutputNumber applyLogic(GameRoundInput gameRoundInput) {
        return new OutputNumber(gameRoundInput.getValue() / DIVIDER);
    }
}
