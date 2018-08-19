package com.challenge.reactive.application.service;

import com.challenge.reactive.model.Action;
import com.challenge.reactive.application.repository.ActionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ActionService {

    private ActionsRepository actionsRepository;

    @Autowired
    public ActionService(ActionsRepository actionsRepository) {
        this.actionsRepository = actionsRepository;
    }

    /**
     * Adds new action to source.
     */
    public Mono<Action> addAction(Action action) {
        return actionsRepository.save(action);
    }
}
