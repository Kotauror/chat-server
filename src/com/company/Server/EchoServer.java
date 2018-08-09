package com.company.Server;

import com.company.StandardIOHandler;
import com.company.SocketIOHandler;

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
            connectWithSocket();
        }
    }

    public boolean runServer() {
        return true;
    }

    private void connectWithSocket() {
        try {
            Socket clientSocket = this.serverSocket.accept();
            standardIOHandler.informOfNewSocket();
            echo(clientSocket);
        } catch (IOException exception) {
            standardIOHandler.informOfException(this.portNumber, exception.getMessage());
        }
    }

    private static void echo(Socket clientSocket) throws IOException {
        SocketIOHandler socketIOHandler = new SocketIOHandler(clientSocket);
        String inputLine;
        while ((inputLine = socketIOHandler.readFromSocket()) != null) {
            socketIOHandler.printToSocket(inputLine);
        }
    }
}
