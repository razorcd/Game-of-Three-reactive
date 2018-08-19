package com.challenge.reactive.application.e2e;

import com.challenge.reactive.model.Game;
import com.challenge.reactive.application.repository.GameRoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class GameIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    GameRoomRepository gameRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.dropCollection(Game.class);
    }

    @Test
    public void should_create_new_game() {
        webTestClient.post().uri("/games").contentType(MediaType.APPLICATION_JSON).body(null)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();

        assertEquals("Should create new game.", 1, gameRepository.count().block().intValue());
    }

    @Test
    public void should_get_all_games() {
        List<Game> gamesInDb = gameRepository.saveAll(Arrays.asList(new Game(), new Game())).collectList().block();

        List<Game> result = webTestClient.get().uri("/games").accept(MediaType.APPLICATION_STREAM_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(new ParameterizedTypeReference<Game>(){})
                    .getResponseBody()
                    .collectList()
                    .block();

        assertThat("Should get all games.", gamesInDb, containsInAnyOrder(result.toArray()));
    }
//
//    @Test
//    public void should_add_players_to_game_by_id() {
//        Player player = new Player("IT-id-1");
//        Game gameInDb = gameRepository.saveAll(Arrays.asList(new Game(), new Game())).blockLast();
//
//        webTestClient.post()
//                        .uri("/games/{id}/players", gameInDb.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(Mono.just(player), Player.class)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody().isEmpty();
//
//        Game updatedGameInDb = gameRepository.findById(gameInDb.getId()).block();
//        assertEquals("Should add new player to game.", player, updatedGameInDb.getPlayers().get(0));
//    }
//
//    @Test
//    public void should_add_players_max_2_players_to_game_by_id() {
//        Player player = new Player("IT-id-1");
//        Game gameInDb = new Game(null, Arrays.asList(new Player("p1"), new Player("p2")));
//        gameInDb = gameRepository.save(gameInDb).block();
//
//        webTestClient.post()
//                .uri("/games/{id}/players", gameInDb.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(player), Player.class)
//                .exchange()
//                    .expectStatus().isBadRequest()
//                    .expectBody()
//                    .jsonPath("$.message").isEqualTo("players: size must be maximum 2");
//    }
}



