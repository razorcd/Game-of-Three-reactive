package com.challenge.reactive.gameofthree.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor
public class Game {

    @Id
    private String id;

//    @Max(2)
    List<Player> players = new ArrayList<>();

//    private List<GameRound> gameRound;
}
