package com.challenge.reactive.processor;

import com.challenge.reactive.gameofthree.GameOfThree;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@Import(GameOfThree.class)
public class ProcessorApplication {
}
