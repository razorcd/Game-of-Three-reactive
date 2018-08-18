package com.challenge.reactive.application.service;

import com.challenge.reactive.application.model.Game;
import com.challenge.reactive.application.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
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

//    @Test
//    public void method_addPlayer_should_add_a_player_to_a_game() {
//        Game gameInDb = gameRepository.saveAll(Arrays.asList(new Game(), new Game())).blockLast();
//        Player player = new Player("id1");
//
//        gameService.addPlayer(player, gameInDb.getId()).block();
//
//        Game updatedGameInDb = gameRepository.findById(gameInDb.getId()).block();
//        assertEquals("Should add player to game.", player, updatedGameInDb.getPlayers().get(0));
//    }
//
//    @Test
//    public void method_addPlayer_should_add_max_2_players_to_a_game() {
//        Game gameInDb = new Game(null, Arrays.asList(new Player("id-1"), new Player("id-2")));
//        gameInDb = gameRepository.save(gameInDb).block();
//        Player player = new Player("id-3");
//
//        gameService.addPlayer(player, gameInDb.getId()).block();
//
//        Game updatedGameInDb = gameRepository.findById(gameInDb.getId()).block();
//        assertEquals("Should add player to game.", player, updatedGameInDb.getPlayers().get(2));
//    }
}