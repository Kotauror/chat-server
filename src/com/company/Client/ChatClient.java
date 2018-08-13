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

    public void run() throws IOException {
        standardIOHandler.informOfConnectionToServer();
        String clientInput;
        while ((clientInput = standardIOHandler.readFromStdIn()) != null) {
            socketIOHandler.printToSocket(clientInput);
            String messageFromSocket = socketIOHandler.readFromSocket();
            standardIOHandler.echoToStdOut(messageFromSocket);
        }
    }
}
