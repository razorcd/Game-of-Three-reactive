package com.challenge.reactive.processor.runner;

import com.challenge.reactive.gameofthree.game.IGameService;
import com.challenge.reactive.model.Action;
import com.challenge.reactive.processor.commands.*;
import com.challenge.reactive.processor.service.ICommandLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class CommandController implements Consumer<Action> {

    private IGameService gameService;
    private ICommandLogService socketChannel;
    private List<ChainableCommand<Action>> chainableCommandList;


    /**
     * Controller to handle user input commands.
     *
     * @param gameService service to interact with running game.
     * @param socketChannel the communication socket.
     */
    @Autowired
    public CommandController(IGameService gameService, ICommandLogService socketChannel, List<ChainableCommand<Action>> chainableCommandList) {
        this.gameService = gameService;
        this.socketChannel = socketChannel;

        this.chainableCommandList = chainableCommandList;
    }

    /**
     * Execute user input command.
     *
     * @param action the action to process.
     */
    @Override
    public void accept(Action action) {
        runCommand(action);
    }

    private void runCommand(Action action) {
        switch (action.getCommand()) {
            case ADD_PLAYER:
                AddHuman addHumanBean = (AddHuman) chainableCommandList.stream().filter(bean -> bean instanceof AddHuman).findFirst().get();
                addHumanBean.execute(action);
                break;
            case ADD_MACHINE:
                new AddMachine(gameService, socketChannel).execute(action);
                break;
            case START:
                Start start = new Start(gameService, socketChannel);
//                PlayMachine playMachine = new PlayMachine(gameService, commandGameLogRepository, new DivideByThreeAi());
//                start.setSuccessor(playMachine);
                start.execute(action);
                break;
            case PLAY:
                Play playCommand = new Play(gameService, socketChannel);
//                playCommand.setSuccessor(new PlayMachine(gameService, socketChannel, new DivideByThreeAi()));
                playCommand.execute(action);
                break;
        }
    }
}
