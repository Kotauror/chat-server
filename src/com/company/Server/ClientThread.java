package com.company.Server;

import com.company.SocketIOHandler;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private final Socket clientSocket;
    private String clientName;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleUserInput(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return this.clientName;
    }

    public Socket getSocket() {
        return this.clientSocket;
    }

    private void handleUserInput(Socket clientSocket) throws IOException {
        SocketIOHandler socketIOHandler = new SocketIOHandler(clientSocket);
        String userInput;
        while ((userInput = socketIOHandler.readFromSocket()) != null) {
            if (shouldSetName(userInput)) {
                setClientName(userInput, socketIOHandler);
            } else if (shouldGetUsers(userInput)) {
                getUserNames(socketIOHandler);
            } else {
                echoWord(userInput, socketIOHandler);
            }
            handleUserInput(clientSocket);
        }
    }

    private boolean shouldSetName(String userInput) {
        return userInput.substring(0, Math.min(userInput.length(), 5)).equals("$NAME");
    }

    private void setClientName(String userInput, SocketIOHandler socketIOHandler) {
        this.clientName = userInput.substring(6, userInput.length()).trim();
        socketIOHandler.printToSocket("Your name was set to be " + this.clientName);
    }

    private boolean shouldGetUsers(String userInput) {
        return userInput.substring(0, Math.min(userInput.length(), 6)).equals("$USERS");
    }

    private void getUserNames(SocketIOHandler socketIOHandler) {
        String userNames = ClientBase.getClientsNames();
        socketIOHandler.printToSocket("There are following users: " + userNames);
    }

    private void echoWord(String userInput, SocketIOHandler socketIOHandler) {
        socketIOHandler.printToSocket(userInput);
    }
}
