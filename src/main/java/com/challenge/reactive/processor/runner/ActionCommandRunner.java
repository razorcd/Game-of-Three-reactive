package com.challenge.reactive.processor.runner;

import com.challenge.reactive.application.repository.ActionsRepository;
import com.challenge.reactive.gameofthree.game.GameService;
import com.challenge.reactive.processor.repository.CommandActionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Will be executed at reactive start.
 */
@Component
public class ActionCommandRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionCommandRunner.class);

    private final ActionsRepository commandActionsRepository;

    private final GameService gameService;

    @Autowired
    public ActionCommandRunner(ActionsRepository commandActionsRepository, GameService gameService) {
        this.commandActionsRepository = commandActionsRepository;
        this.gameService = gameService;
    }

    @Override
    public void run(String... strings) throws Exception {
        commandActionsRepository.findWithTailableCursorBy()
                .doOnNext(action -> {
                    LOGGER.info("Received action from repository: {}", action);

                })
                .doOnError(error -> LOGGER.warn("Error on received action from repository. ", error));
    }
}