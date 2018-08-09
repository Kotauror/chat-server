package com.company.Server;

import com.company.StandardInOutHandler;
import com.company.SocketIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private final int portNumber;
    private ServerSocket serverSocket;
    private StandardInOutHandler standardInOutHandler;

    public EchoServer(ServerSocket serverSocket, StandardInOutHandler standardInOutHandler) {
        this.serverSocket = serverSocket;
        this.standardInOutHandler = standardInOutHandler;
        this.portNumber = this.serverSocket.getLocalPort();
    }

    public void run() {
        standardInOutHandler.printServerPort(this.portNumber);
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
            standardInOutHandler.informOfNewSocket();
            echo(clientSocket);
        } catch (IOException exception) {
            standardInOutHandler.informOfException(this.portNumber, exception.getMessage());
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
