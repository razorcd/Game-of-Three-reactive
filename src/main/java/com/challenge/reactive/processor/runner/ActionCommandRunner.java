package com.challenge.reactive.processor.runner;

import com.challenge.reactive.application.repository.ActionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Will be executed at application start.
 */
@Component
public class ActionCommandRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionCommandRunner.class);

    private final ActionsRepository actionsRepository;

    public ActionCommandRunner(ActionsRepository actionsRepository) {
        this.actionsRepository = actionsRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        actionsRepository.findWithTailableCursorBy()
                .doOnNext(action -> {
                    LOGGER.info("Received action from repository: {}", action);
                })
                .doOnError(error -> LOGGER.warn("Error on received action from repository. ", error));
    }

}