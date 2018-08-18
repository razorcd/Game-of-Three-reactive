package com.challenge.reactive.application.repository;

import com.challenge.reactive.application.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Repository
public interface GameRepository extends ReactiveMongoRepository<Game, String> {

    <S extends Game> Mono<S> save(@Valid S entity);
}