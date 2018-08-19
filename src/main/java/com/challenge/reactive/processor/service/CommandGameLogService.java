package com.challenge.reactive.processor.service;

import org.springframework.stereotype.Service;

@Service
public class CommandGameLogService implements ICommandGameLogService {

    /**
     * Send message to output.
     *
     * @param message the message to sent.
     */
    @Override
    public void send(String message) {

    }

    /**
     * Broadcast message to all active socket listeners.
     *
     * @param message the message to broadcast.
     */
    @Override
    public void broadcast(String message) {

    }
}
