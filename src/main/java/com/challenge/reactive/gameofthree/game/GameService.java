package com.challenge.reactive.gameofthree.game;

import com.challenge.reactive.gameofthree.exception.GameException;
import com.challenge.reactive.gameofthree.game.domain.InputNumber;
import com.challenge.reactive.gameofthree.game.validator.*;
import com.challenge.reactive.gameofthree.model.IPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class GameService implements IGameService {
    private final AtomicReference<Game> game;

    /**
     * Build reactive context.
     */
    @Autowired
    public GameService(Game game) {
        this.game = new AtomicReference<>(game);
    }

    /**
     * Add new player to the game.
     *
     * @param player player to add to player list.
     * @throws GameException if not unique player.
     */
    public void addPlayer(final IPlayer player) {
        new UniquePlayerValidator(player).validateOrThrow(game.get());
        new IsOpenPlayerAggregateValidator().validateOrThrow(game.get());
        game.set(game.get().addPlayer(player));
    }

    /**
     * Remove a player from the game.
     *
     * @param player the player to remove.
     */
    public void removePlayer(IPlayer player) {
        game.set(game.get().removePlayer(player));
    }

    /**
     * Start playing the game with the added players.
     *
     * @throws GameException if not enough players.
     */
    public void startGame() {
        game.get().validateOrThrow(new NewGameValidator());
        game.set(game.get().startGame());
    }

    /**
     * Stop playing game. Sets game to initial state while keeping the players.
     */
    public void stopGame() {
        game.set(game.get().stopGame());
    }

    /**
     * Play a number.
     * Validates the input and the game and plays the number.
     * Changes state of manager and adds new game.
     *
     * @param inputNumber the number to play.
     * @param playerInTurn the current authorized player
     * @throws GameException if not enough players or last round result is not valid or invalid input.
     */
    public void play(final InputNumber inputNumber, final IPlayer playerInTurn) {
        game.get().validateOrThrow(new CanPlayGameValidator());
        game.get().validateOrThrow(new IsCurrentPlayerGameValidator(playerInTurn));
        game.get().validateOrThrow(new InputNumberWithinRangeValidator(inputNumber));

        Game newGame = game.get().play(inputNumber);
        game.set(newGame);
    }

    /**
     * Get game in current state.
     *
     * @return [Game] game in current state.
     */
    public Game getGame() {
        return game.get();
    }
}
