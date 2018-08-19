package com.challenge.reactive.application.controller;

import com.challenge.reactive.model.Game;
import com.challenge.reactive.application.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("games")
public class GameController {

    private final GameRoomService gameService;

    @Autowired
    public GameController(GameRoomService gameService) {
        this.gameService = gameService;
    }

    /**
     * Get all games.
     *
     * @return {@code Flux<Game>} all games.
     */
    @GetMapping
    public Flux<Game> getGames() {
        return gameService.getGames();
    }

    /**
     * Create a new game.
     *
     * @return {@code Mono<ResponseEntity>} empty.
     */
    @PostMapping
    public Mono<ResponseEntity> postGame() {
        return gameService.createGame()
                .map(game -> new ResponseEntity(HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.BAD_REQUEST));
    }

//    /**
//     * Add player to a game by gameId.
//     *
//     * @return {@code Flux<ResponseEntity>} empty.
//     */
//    @PostMapping("{gameId}/players")
//    public Mono<ResponseEntity> addPlayerToGame(@PathVariable String gameId, @RequestBody Player player) {
//        return gameService.addPlayer(player, gameId)
//                .map(game -> new ResponseEntity(HttpStatus.CREATED))
//                .defaultIfEmpty(new ResponseEntity(HttpStatus.BAD_REQUEST));
//    }


//    @Autowired
//    private GameActionsRepository gameActionsRepository;
//
//    @GetMapping
//    public Flux<GameAction> getGameAction(@PathVariable String id) {
//        System.out.println("!!!! GET");
////        return new Game(gameActionsRepository).getActions();
//        return gameActionsRepository
//                .findWithTailableCursorBy()
//                .log();
//
////        return Flux.just(
////                new GameAction("START", null),
////                new GameAction("PLAY", "-1"),
////                new GameAction("PLAY", "0")
////            )
////                .delayElements(Duration.of(2, ChronoUnit.SECONDS));
//    }
//
//    @PostMapping
//    public void postGameAction() {
//        System.out.println("!!!! POST");
//        GameAction gameAction = new GameAction(null, "START", "-1");
////        new Game(gameActionsRepository).addAction(gameAction);
//        gameActionsRepository.save(gameAction).block();
//        System.out.println(gameActionsRepository.count().block());
//    }

}
