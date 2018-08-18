package com.challenge.reactive.application.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document
@Data
@NoArgsConstructor
public class Action {

    @Id
    private String id;

    @Indexed
    @NotEmpty
    private String gameId;

    @NotNull
    private CommandType command;

    private String parameter;

    @NotEmpty
    private String actionOwner;

    public Action(String actionOwner, CommandType command, String parameter, String gameId) {
        this.actionOwner = actionOwner;
        this.command = command;
        this.parameter = parameter;
        this.gameId = gameId;
    }
}
