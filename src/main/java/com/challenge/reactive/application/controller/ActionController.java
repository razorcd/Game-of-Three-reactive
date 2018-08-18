package com.challenge.reactive.application.controller;

import com.challenge.reactive.application.model.Action;
import com.challenge.reactive.application.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("actions")
public class ActionController {

    private final ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    /**
     * Create a new action.
     *
     * @return {@code Mono<ResponseEntity>} empty.
     */
    @PostMapping
    public Mono<ResponseEntity> postGame(@RequestBody Action action) {
        return actionService.addAction(action)
                .map(game -> new ResponseEntity(HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.BAD_REQUEST));
    }
}
