package com.challenge.reactive.gameofthree.service;

import com.challenge.reactive.gameofthree.model.Game;
import com.challenge.reactive.gameofthree.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
//@Transactional
public class GameServiceTest {

    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {
        gameRepository.deleteAll().block();
        gameService = new GameService(gameRepository);
    }

    @Test
    public void method_createGame_should_create_new_game() {
        gameService.createGame().block();

        int createdGameCount = Objects.requireNonNull(gameRepository.count().block()).intValue();
        assertEquals("Should create and persist new game.", 1, createdGameCount);
    }

    @Test
    public void method_getGames_should_return_all_games() {
        gameRepository.saveAll(Arrays.asList(new Game(), new Game())).blockLast();

        List<Game> games = gameService.getGames().collectList().block();

        assertThat("Should return all games.", games, hasSize(2));
    }

}