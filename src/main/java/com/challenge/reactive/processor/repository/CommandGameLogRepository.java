package com.challenge.reactive.processor.repository;

import com.challenge.reactive.model.GameLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandGameLogRepository extends ReactiveMongoRepository<GameLog, String> {
}