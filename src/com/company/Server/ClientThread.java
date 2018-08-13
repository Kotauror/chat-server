package com.company.Server;

import com.company.SocketIOHandler;

import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {

    private final Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            echo(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
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
