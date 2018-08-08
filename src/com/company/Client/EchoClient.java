package com.company.Client;

import com.company.SocketIOHandler;
import com.company.StandardInOutHandler;

import java.io.IOException;
import java.net.Socket;

public class EchoClient {

    private final Socket clientSocket;
    private final SocketIOHandler socketIOHandler;
    private final StandardInOutHandler standardInOutHandler;

    public EchoClient(Socket clientSocket, SocketIOHandler socketIOHandler, StandardInOutHandler standardInOutHandler) {
        this.clientSocket = clientSocket;
        this.socketIOHandler = socketIOHandler;
        this.standardInOutHandler = standardInOutHandler;
    }

    public void run() throws IOException {
        String clientInput;
        while ((clientInput = standardInOutHandler.readFromStdIn()) != null) {
            socketIOHandler.printToSocket(clientInput);
            String messageFromSocket = socketIOHandler.readFromSocket();
            standardInOutHandler.echoToStdOut(messageFromSocket);
        }
    }
}
