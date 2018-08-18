package com.challenge.reactive.gameofthree.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Game {

    @Id
    private String id;

//    private List<GameRound> gameRound;
}
