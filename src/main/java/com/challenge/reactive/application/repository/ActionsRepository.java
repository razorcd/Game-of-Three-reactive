package com.challenge.reactive.application.repository;

import com.challenge.reactive.model.Action;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ActionsRepository extends ReactiveMongoRepository<Action, String> {

    /**
     * Tailable search for all actions.
     *
     * @return {@code Flux<Action>} reactive flux of all actions.
     */
    @Tailable
    Flux<Action> findWithTailableCursorBy();

}