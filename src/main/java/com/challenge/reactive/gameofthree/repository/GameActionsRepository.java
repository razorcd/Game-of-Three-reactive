package com.challenge.reactive.gameofthree.repository;

import com.challenge.reactive.gameofthree.model.GameAction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GameActionsRepository extends ReactiveMongoRepository<GameAction, String> {

    @Tailable
    Flux<GameAction> findWithTailableCursorBy();

}