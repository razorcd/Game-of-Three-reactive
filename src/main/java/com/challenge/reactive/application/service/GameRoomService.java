package com.challenge.reactive.application.service;

import com.challenge.reactive.model.Game;
import com.challenge.reactive.application.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameRoomService {

    private final RoomRepository gameRepository;

    @Autowired
    public GameRoomService(RoomRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Create new game.
     *
     * @return {@code Mono<Game>} the new game.
     */
    public Mono<Game> createGame() {
        return gameRepository.save(new Game());
    }

    /**
     * Get all games.
     *
     * @return {@code Flux<Game>} all games.
     */
    public Flux<Game> getGames() {
        return gameRepository.findAll();
    }

//    /**
//     * Adds new player to game.
//     *
//     * @param player the new player to add
//     * @return {@code Mono<Game>} the game.
//     */
//    public Mono<Game> addPlayer(Player player, String gameId) {
//         return gameRepository.findById(gameId)
//                 .flatMap(game -> addPlayerToGame(player, game));
//    }
//
//    private Mono<Game> addPlayerToGame(Player player, Game game) {
//        List<Player> newPlayerList = new ArrayList<>(game.getPlayers());
//        newPlayerList.add(player);
//        game.setPlayers(newPlayerList);
//        return gameRepository.save(game);
//    }
}
