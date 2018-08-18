package com.challenge.reactive.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class GameOfThreeApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void actuatorMainAccessible() throws Exception {
        webTestClient.get().uri("/actuator")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void actuatorHealthAccessible() throws Exception {
        webTestClient.get().uri("/actuator/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"status\":\"UP\"}");
    }
}
