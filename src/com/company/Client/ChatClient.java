package com.company.Client;

import com.company.SocketIOHandler;
import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private final Socket clientSocket;
    private final SocketIOHandler socketIOHandler;
    private final StandardIOHandler standardIOHandler;

    public ChatClient(Socket clientSocket, SocketIOHandler socketIOHandler, StandardIOHandler standardIOHandler) {
        this.clientSocket = clientSocket;
        this.socketIOHandler = socketIOHandler;
        this.standardIOHandler = standardIOHandler;
    }

    public void run() {
        standardIOHandler.informOfConnectionToServer();
        standardIOHandler.informOfRules();
        listenForPrompts();
        listenForMessages();
    }

    private void listenForPrompts() {
        PromptsThread promptsThread = new PromptsThread(this.standardIOHandler, this.socketIOHandler);
        promptsThread.start();
    }

    private void listenForMessages() {
        MessagesThread messagesThread = new MessagesThread(this.standardIOHandler, this.socketIOHandler);
        messagesThread.start();
    }
}
