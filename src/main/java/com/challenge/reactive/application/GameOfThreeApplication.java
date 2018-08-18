package com.challenge.reactive.application;

import com.challenge.reactive.gameofthree.util.PropertiesConfigLoader;
import com.challenge.reactive.processor.ProcessorApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories
@EnableReactiveMongoRepositories
@Import(ProcessorApplication.class)
public class GameOfThreeApplication {
    static {
        PropertiesConfigLoader.initialize("application.properties");
    }

    public static void main(String[] args) {
        SpringApplication.run(GameOfThreeApplication.class, args);
    }
}
