package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private ServerSocket serverSocket;

    public EchoServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private Socket accept() throws IOException {
        return this.serverSocket.accept();
    }

    public void run() {
        try {
            Socket clientSocket = accept();
            echo(clientSocket);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port");
            System.out.println(e.getMessage());
        }
    }

    private static void echo(Socket clientSocket) throws IOException {
        IOHandler iOHandler = new IOHandler(clientSocket);
        String inputLine;
        while ((inputLine = iOHandler.readFromSocket()) != null) {
                iOHandler.printToSocket(inputLine);
        }
    }
}
