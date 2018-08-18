package com.challenge.reactive.gameofthree.service;

import com.challenge.reactive.gameofthree.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
        gameRepository.deleteAll();
        gameService = new GameService(gameRepository);
    }

    @Test
    public void method_createGame_should_create_new_game() {
        gameService.createGame()
                .block();

        assertEquals("Should create and persist new game.", 1, gameRepository.count().block().intValue());
    }

}