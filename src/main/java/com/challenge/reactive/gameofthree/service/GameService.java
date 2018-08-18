package com.challenge.reactive.gameofthree.service;

import com.challenge.reactive.gameofthree.model.Game;
import com.challenge.reactive.gameofthree.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Mono<Game> createGame() {
        return gameRepository.save(new Game());
    }
}
