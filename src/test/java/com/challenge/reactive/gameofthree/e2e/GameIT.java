package com.challenge.reactive.gameofthree.e2e;

import com.challenge.reactive.gameofthree.model.Game;
import com.challenge.reactive.gameofthree.repository.GameRepository;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
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
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {
        gameRepository.deleteAll().block();
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
}



