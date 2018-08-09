package com.company.Server;

import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private final int portNumber;
    private ServerSocket serverSocket;
    private StandardIOHandler standardIOHandler;

    public EchoServer(ServerSocket serverSocket, StandardIOHandler standardIOHandler) {
        this.serverSocket = serverSocket;
        this.standardIOHandler = standardIOHandler;
        this.portNumber = this.serverSocket.getLocalPort();
    }

    public void run() {
        standardIOHandler.printServerPort(this.portNumber);
        while (runServer()) {
            connectWithClients();
        }
    }

    public boolean runServer() {
        return true;
    }

    private void connectWithClients() {
        try {
            Socket clientSocket = this.serverSocket.accept();
            Thread clientThread = new ClientThread(clientSocket);
            clientThread.start();
        } catch (IOException exception) {
            standardIOHandler.informOfException(this.portNumber, exception.getMessage());
        }
    }
}
