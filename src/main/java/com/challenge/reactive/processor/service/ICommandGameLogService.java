package com.challenge.reactive.processor.service;

public interface ICommandGameLogService {

    /**
     * Send message to output.
     *
     * @param message the message to sent.
     */
    void send(String message);

    /**
     * Broadcast message to all active socket listeners.
     *
     * @param message the message to broadcast.
     */
    void broadcast(String message);
}
