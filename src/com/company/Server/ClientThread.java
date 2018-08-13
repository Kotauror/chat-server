package com.company.Server;

import com.company.SocketIOHandler;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private final Socket clientSocket;
    private final SocketIOHandler socketIOHandler;
    private String clientName;

    public ClientThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientName = this.getName();
        this.socketIOHandler = new SocketIOHandler(this.clientSocket);
    }

    @Override
    public void run() {
        try {
            handleUserInput(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return this.clientName;
    }

    public Socket getSocket()
    {
        return this.clientSocket;
    }

    public SocketIOHandler getSocketIOHandler() {
        return this.socketIOHandler;
    }

    private void handleUserInput(Socket clientSocket) throws IOException, IllegalAccessException {
        String userInput;
        while ((userInput = this.socketIOHandler.readFromSocket()) != null) {
            if (shouldSetName(userInput)) {
                setClientName(userInput);
            } else if (shouldGetUsers(userInput)) {
                getUserNames();
            } else if (shouldSendToOtherUser(userInput)) {
                sendToOtherUser(userInput);
            } else {
                echoWord(userInput);
            }
            handleUserInput(clientSocket);
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
        String userNames = ClientBase.getClientsNames();
        this.socketIOHandler.printToSocket("There are following users: " + userNames);
    }

    private boolean shouldSendToOtherUser(String userInput) {
        return userInput.substring(0, Math.min(userInput.length(), 8)).equals("$MESSAGE");
    }

    private void sendToOtherUser(String userInput) throws IllegalAccessException, IOException {
        ClientThread clientThread = ClientBase.getClientThread("kot");
        clientThread.getSocketIOHandler().printToSocket("hehhegrigbewuigh");
        this.socketIOHandler.printToSocket("Message has been sent.");
    }

    private void echoWord(String userInput) {
        this.socketIOHandler.printToSocket(userInput);
    }
}
