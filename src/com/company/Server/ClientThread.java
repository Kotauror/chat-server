package com.company.Server;

import com.company.Messages;
import com.company.SocketIOHandler;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private final SocketIOHandler socketIOHandler;
    private final ChatServer chatServer;
    private String clientName;

    public ClientThread (Socket clientSocket, ChatServer chatServer) throws IOException {
        this.chatServer = chatServer;
        this.clientName = this.getName();
        this.socketIOHandler = new SocketIOHandler(clientSocket);
    }

    @Override
    public void run() {
        try {
            printInitialMessages();
            handleUserInput();
        } catch (IOException e) {
            this.socketIOHandler.printToSocket("Client cannot be successfully run.");
        }
    }

    public String getClientName() {
        return this.clientName;
    }

    public SocketIOHandler getSocketIOHandler() {
        return this.socketIOHandler;
    }

    private void printInitialMessages() {
        socketIOHandler.printToSocket(Messages.informOfConnectionToServer());
        socketIOHandler.printToSocket(Messages.getPrompts());
    }

    private void handleUserInput() throws IOException {
        String userInput;
        while ((userInput = this.socketIOHandler.readFromSocket()) != null) {
            if (shouldSetName(userInput)) {
                setClientName(userInput);
            } else if (shouldGetUsers(userInput)) {
                getUserNames();
            } else if (shouldSendToOtherUser(userInput)) {
                sendToOtherUser(userInput);
            }
            handleUserInput();
        }
    }

    private boolean shouldSetName(String userInput) {
        return userInput.substring(0, Math.min(userInput.length(), 5)).equals("$NAME");
    }

    private void setClientName(String userInput) {
        this.clientName = userInput.substring(6, userInput.length()).trim();
        this.socketIOHandler.printToSocket("Your name was set to be " + this.clientName);
    }

    private boolean shouldGetUsers(String userInput) {
        return userInput.substring(0, Math.min(userInput.length(), 6)).equals("$USERS");
    }

    private void getUserNames() {
        String userNames = this.chatServer.getClientNames();
        this.socketIOHandler.printToSocket("There are following users: " + userNames);
    }

    private boolean shouldSendToOtherUser(String userInput) {
        return userInput.substring(0, Math.min(userInput.length(), 8)).equals("$MESSAGE");
    }

    private void sendToOtherUser(String userInput) {
        try {
            this.chatServer.sendMessage(userInput);
            this.socketIOHandler.printToSocket("Message has been sent.");
        } catch (IllegalAccessException e) {
            this.socketIOHandler.printToSocket("Message not sent - there is no such user.");
        }
    }
}
