package com.challenge.reactive.gameofthree.service;

import com.challenge.reactive.gameofthree.model.Game;
import com.challenge.reactive.gameofthree.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Create new game.
     *
     * @return {@code Mono<Game>} the new game.
     */
    public Mono<Game> createGame() {
        return gameRepository.save(new Game());
    }

    /**
     * Get all games.
     *
     * @return {@code Flux<Game>} all games.
     */
    public Flux<Game> getGames() {
        return gameRepository.findAll();
    }
}
