package com.challenge.reactive.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories
@EnableReactiveMongoRepositories
public class GameOfThreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameOfThreeApplication.class, args);
    }
}
