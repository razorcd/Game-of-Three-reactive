package com.challenge.reactive.gameofthree.e2e;

import com.challenge.reactive.gameofthree.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

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
        webTestClient.post().uri("/games")
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();

        assertEquals("Should create new game.", 1, gameRepository.count().block().intValue());
    }
}



