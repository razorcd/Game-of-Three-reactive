package com.challenge.reactive.application.service;

import com.challenge.reactive.model.Action;
import com.challenge.reactive.application.repository.ActionsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static com.challenge.reactive.model.CommandType.ADD_PLAYER;
import static com.challenge.reactive.model.CommandType.PLAY;
import static com.challenge.reactive.model.CommandType.START;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ActionServiceTest {

    private ActionService actionService;

    @Autowired
    private ActionsRepository actionsRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.dropCollection(Action.class);
        actionService = new ActionService(actionsRepository);
    }

    @Test
    public void method_addAction_should_add_actions_to_source() {
        Action action1 = new Action("actionOwner-idA", ADD_PLAYER, "param1", "game-id1");
        Action action2 = new Action("actionOwner-idA", START, "param1", "game-id1");
        Action action3 = new Action("actionOwner-idB", PLAY, "param2", "game-id2");

        actionService.addAction(action1).block();
        actionService.addAction(action2).block();
        actionService.addAction(action3).block();

        List<Action> createdGameActions = actionsRepository.findAll().collectList().block();
        assertThat("Should add 3 actions to game.", createdGameActions, hasSize(3));
        assertThat("Should create actions with correct owners.",
                createdGameActions.stream().map(Action::getActionOwner).collect(Collectors.toList()),
                contains("actionOwner-idA", "actionOwner-idA", "actionOwner-idB"));
        assertThat("Should create actions with correct commands.",
                createdGameActions.stream().map(Action::getCommand).collect(Collectors.toList()),
                contains(ADD_PLAYER, START, PLAY));
        assertThat("Should create actions with correct parameters.",
                createdGameActions.stream().map(Action::getParameter).collect(Collectors.toList()),
                contains("param1", "param1", "param2"));
        assertThat("Should create actions with correct gameIds.",
                createdGameActions.stream().map(Action::getGameId).collect(Collectors.toList()),
                contains("game-id1", "game-id1", "game-id2"));
    }
}