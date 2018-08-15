package com.company.Client;

import com.company.SocketIOHandler;
import com.company.StandardIOHandler;

import java.util.concurrent.Executor;

public class ChatClient {

    private final SocketIOHandler socketIOHandler;
    private final StandardIOHandler standardIOHandler;
    private final Executor executor;

    public ChatClient(SocketIOHandler socketIOHandler, StandardIOHandler standardIOHandler, Executor executor) {
        this.socketIOHandler = socketIOHandler;
        this.standardIOHandler = standardIOHandler;
        this.executor = executor;
    }

    public void run() {
        listenForPrompts();
        listenForMessages();
    }

    private void listenForPrompts() {
        PromptsThread promptsThread = new PromptsThread(this.standardIOHandler, this.socketIOHandler);
        executor.execute(promptsThread);
    }

    private void listenForMessages() {
        MessagesThread messagesThread = new MessagesThread(this.standardIOHandler, this.socketIOHandler);
        executor.execute(messagesThread);
    }
}
