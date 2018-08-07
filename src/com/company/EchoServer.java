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
        IOHandler iOHandler = new IOHandler(clientSocket);
        echo(iOHandler);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port");
            System.out.println(e.getMessage());
        }
    }

    private static void echo(IOHandler iOHandler) throws IOException {
        String inputLine = iOHandler.readFromSocket();
        while (inputLine != null) {
            iOHandler.printToSocket(inputLine);
            echo(iOHandler);
        }
    }
}
