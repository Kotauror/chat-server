package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private ServerSocket serverSocket;
    private int portNumber;

    public EchoServer(ServerSocket serverSocket, int portNumber) {
        this.serverSocket = serverSocket;
        this.portNumber = portNumber;
    }

    public void run() {
        printServerInfo(portNumber);
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
            echo(clientSocket);
        } catch (IOException exception) {
            informOfException(exception);
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

    private void informOfException (IOException exception) {
        System.out.println("Exception caught when trying to listen on port");
        System.out.println(exception.getMessage());
    }

    private void printServerInfo(int portNumber) {
        System.out.println("Listening on port " + portNumber);
    }
}
