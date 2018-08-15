package com.company.Server;

import com.company.SocketIOHandler;
import com.company.Messages;

import java.io.IOException;

public class ClientThread extends Thread {

    private final SocketIOHandler socketIOHandler;
    private final ChatServer chatServer;
    private String clientName;

    public ClientThread (SocketIOHandler socketIOHandler, ChatServer chatServer) {
        this.socketIOHandler = socketIOHandler;
        this.chatServer = chatServer;
        this.clientName = this.getName();
    }

    @Override
    public void run() {
        try {
            this.socketIOHandler.printToSocket(Messages.informOfConnectionToServer());
            this.socketIOHandler.printToSocket(Messages.getRules());
            handleUserInput();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return this.clientName;
    }

    public SocketIOHandler getSocketIOHandler() {
        return this.socketIOHandler;
    }

    private void handleUserInput() throws IOException, IllegalAccessException {
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
            e.printStackTrace();
            this.socketIOHandler.printToSocket("Message not sent");
        }
    }
}
