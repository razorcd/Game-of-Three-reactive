package com.challenge.reactive.application.repository;

import com.challenge.reactive.model.GameLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ActionLogRepository extends ReactiveMongoRepository<GameLog, String> {

    /**
     * Get all broadcasted messages for a game.
     *
     * @param gameId the game id to search by
     * @return {@code Flux<GameLog>} reactive flux of all broadcasted logs to a game.
     */
    @Tailable
    Flux<GameLog> findWithTailableCursorByGameIdAndBroadcastTrue(String gameId);

    /**
     * Get all not broadcasted messages but specific to one player for a game.
     *
     * @param gameId the game id to search by.
     * @param playerId the player id to search by.
     * @return {@code Flux<GameLog>} reactive flux of all not broadcasted, player specific logs to a game.
     */
    @Tailable
    Flux<GameLog> findWithTailableCursorByGameIdAndBroadcastFalseAndPlayer_Id(String gameId, String playerId);
}