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

    private void handleUserInput(Socket clientSocket) throws IOException {
        SocketIOHandler socketIOHandler = new SocketIOHandler(clientSocket);
        String inputLine;
        while ((inputLine = socketIOHandler.readFromSocket()) != null) {
            if (inputLine.substring(0, Math.min(inputLine.length(), 5)).equals("$NAME")) {
                this.clientName = inputLine.substring(6, inputLine.length()).trim();
                socketIOHandler.printToSocket("Your name was set to be " + this.clientName);
                handleUserInput(clientSocket);
            }  else if (inputLine.substring(0, Math.min(inputLine.length(), 6)).equals("$USERS")) {
                String userNames = ClientBase.getClientsNames();
                socketIOHandler.printToSocket(userNames);
                handleUserInput(clientSocket);
            } else {
                socketIOHandler.printToSocket(inputLine);
            }
        }
    }

    public String getClientName() {
        return this.clientName;
    }
}
