package com.company.Server;

import com.company.StandardInOutHandler;
import com.company.SocketIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private ServerSocket serverSocket;
    private int portNumber;
    private StandardInOutHandler standardInOutHandler;

    public EchoServer(ServerSocket serverSocket, int portNumber, StandardInOutHandler standardInOutHandler) {
        this.serverSocket = serverSocket;
        this.portNumber = portNumber;
        this.standardInOutHandler = standardInOutHandler;
    }

    public void run() {
        standardInOutHandler.printServerPort(portNumber);
        while (runServer()) {
            connectWithSocket();
        }
    }

    public boolean runServer() {
        return true;
    }

    private void connectWithSocket() {
        try {
            Socket clientSocket = accept();
            standardInOutHandler.informOfNewSocket();
            echo(clientSocket);
        } catch (IOException exception) {
            standardInOutHandler.informOfException(this.portNumber, exception.getMessage());
        }
    }

    private Socket accept() throws IOException {
        return this.serverSocket.accept();
    }

    private static void echo(Socket clientSocket) throws IOException {
        SocketIOHandler socketIOHandler = new SocketIOHandler(clientSocket);
        String inputLine;
        while ((inputLine = socketIOHandler.readFromSocket()) != null) {
                socketIOHandler.printToSocket(inputLine);
        }
    }
}
