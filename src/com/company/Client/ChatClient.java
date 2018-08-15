package com.company.Client;

import com.company.Server.ClientThread;
import com.company.SocketIOHandler;
import com.company.StandardIOHandler;

public class ChatClient {

    private final SocketIOHandler socketIOHandler;
    private final StandardIOHandler standardIOHandler;
    private final ClientThread clientThread;

    public ChatClient(SocketIOHandler socketIOHandler, StandardIOHandler standardIOHandler, ClientThread clientThread) {
        this.socketIOHandler = socketIOHandler;
        this.standardIOHandler = standardIOHandler;
        this.clientThread = clientThread;
    }

    public void run() {
        listenForPrompts();
        listenForMessages();
    }

    public void receiveMessage(String message) {
        this.clientThread.getSocketIOHandler().printToSocket(message);
    }

    public ClientThread getClientThread() {
        return this.clientThread;
    }

    public String getClientName() {
        return this.clientThread.getClientName();
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
