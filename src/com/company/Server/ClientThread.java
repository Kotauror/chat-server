package com.company.Server;

import com.company.Messages;
import com.company.Server.PromptActions.PromptAction;
import com.company.Server.PromptActions.PromptActionFactory;
import com.company.SocketIOHandler;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private final SocketIOHandler socketIOHandler;
    private final ChatServer chatServer;
    private final Parser parser;
    private final PromptActionFactory promptActionFactory;
    private String clientName;

    public ClientThread (Socket clientSocket, ChatServer chatServer, Parser parser) throws IOException {
        this.chatServer = chatServer;
        this.clientName = this.getName();
        this.socketIOHandler = new SocketIOHandler(clientSocket);
        this.parser = parser;
        this.promptActionFactory = new PromptActionFactory();
    }

    @Override
    public void run() {
        try {
            printInitialMessages();
            handleUserInput();
        } catch (IOException | IllegalAccessException e) {
            this.socketIOHandler.printToSocket("Client cannot be successfully run.");
        }
    }

    public ChatServer getChatServer() {
        return this.chatServer;
    }

    public String getClientName() {
        return this.clientName;
    }

    public SocketIOHandler getSocketIOHandler() {
        return this.socketIOHandler;
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    public void listenForInput() throws IOException, IllegalAccessException {
        handleUserInput();
    }

    private void printInitialMessages() {
        socketIOHandler.printToSocket(Messages.informOfConnectionToServer());
        socketIOHandler.printToSocket(Messages.getPrompts());
    }

    private void handleUserInput() throws IOException, IllegalAccessException {
        String userInput;
        while ((userInput = this.socketIOHandler.readFromSocket()) != null) {
            String promptActionType = this.parser.getPromptActionType(userInput);
            PromptAction promptAction = this.promptActionFactory.getPromptAction(promptActionType);
            promptAction.run(this, userInput);
        }
    }
}
