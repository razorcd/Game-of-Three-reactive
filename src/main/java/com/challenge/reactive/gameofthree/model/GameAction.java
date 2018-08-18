package com.challenge.reactive.gameofthree.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class GameAction {

    @Id
    private String id;

    @Indexed
    private String gameId;

    private String action;

    private String parameter;
}
