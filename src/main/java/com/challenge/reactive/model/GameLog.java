package com.challenge.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class GameLog {

    @Id
    private String id;

    private Player player;

    private String gameId;

    private boolean error;

    private String message;

    private boolean broadcast;
}
