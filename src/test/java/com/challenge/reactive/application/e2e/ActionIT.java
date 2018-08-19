package com.challenge.reactive.application.e2e;

import com.challenge.reactive.model.Action;
import com.challenge.reactive.application.repository.ActionsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.challenge.reactive.model.CommandType.ADD_PLAYER;
import static com.challenge.reactive.model.CommandType.START;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class ActionIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    ActionsRepository actionsRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.dropCollection(Action.class);
    }

    @Test
    public void should_create_new_actions() {
        Action action1 = new Action("actionOwner-idA-IT", ADD_PLAYER, "param1-IT", "game-id1-IT");
        Action action2 = new Action("actionOwner-idB-IT", START, "param2-IT", "game-id2-IT");


        webTestClient.post().uri("/actions").contentType(MediaType.APPLICATION_JSON).syncBody(action1)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
        webTestClient.post().uri("/actions").contentType(MediaType.APPLICATION_JSON).syncBody(action2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();


        List<Action> createdActions = actionsRepository.findAll().collectList().block();
        assertThat("Should create 2 actions.", createdActions, hasSize(2));
    }

    @Test
    public void should_NOT_create_invalid_actions() {
        Action action1 = new Action("actionOwner-idA-IT", null, null, "game-id1-IT");
        Action action2 = new Action("", START, "param2-IT", "game-id1-IT");
        Action action3 = new Action("ionOwner-idB-IT", START, "param2-IT", "");

        webTestClient.post().uri("/actions").contentType(MediaType.APPLICATION_JSON).syncBody(action1)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("command: must not be null");
        webTestClient.post().uri("/actions").contentType(MediaType.APPLICATION_JSON).syncBody(action2)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("actionOwner: must not be empty");
        webTestClient.post().uri("/actions").contentType(MediaType.APPLICATION_JSON).syncBody(action3)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("gameId: must not be empty");

        List<Action> createdActions = actionsRepository.findAll().collectList().block();
        assertThat("Should create no invalid actions.", createdActions, hasSize(0));
    }
}



