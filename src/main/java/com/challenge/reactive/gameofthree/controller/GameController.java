package com.challenge.reactive.gameofthree.controller;

import com.challenge.reactive.gameofthree.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Mono<ResponseEntity> postGame() {
        return gameService.createGame()
                .map(game -> new ResponseEntity(HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.BAD_REQUEST));
    }




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
