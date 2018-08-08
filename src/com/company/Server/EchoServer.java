package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private ServerSocket serverSocket;
    private int portNumber;
    private ServerMessenger serverMessenger;

    public EchoServer(ServerSocket serverSocket, int portNumber, ServerMessenger serverMessenger) {
        this.serverSocket = serverSocket;
        this.portNumber = portNumber;
        this.serverMessenger = serverMessenger;
    }

    public void run() {
        serverMessenger.printServerPort(portNumber);
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
            serverMessenger.informOfNewSocket();
            echo(clientSocket);
        } catch (IOException exception) {
            serverMessenger.informOfException(this.portNumber, exception.getMessage());
        }
    }

    private Socket accept() throws IOException {
        return this.serverSocket.accept();
    }

    private static void echo(Socket clientSocket) throws IOException {
        IOHandler iOHandler = new IOHandler(clientSocket);
        String inputLine;
        while ((inputLine = iOHandler.readFromSocket()) != null) {
                iOHandler.printToSocket(inputLine);
        }
    }
}
