package com.challenge.reactive.processor.repository;

import com.challenge.reactive.model.Action;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CommandActionsRepository extends ReactiveMongoRepository<Action, String> {

    @Tailable
    Flux<Action> findWithTailableCursorBy();

}