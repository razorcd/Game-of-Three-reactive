package com.challenge.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    private String id;

    @Size(max = 2, message = "size must be maximum 2")
    List<User> players = new ArrayList<>();

//    private List<GameRound> gameRound;
}
