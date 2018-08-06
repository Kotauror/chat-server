package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private ServerSocket serverSocket;

    public EchoServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
        Socket clientSocket = this.serverSocket.accept();
        IOHandler iOHandler = new IOHandler(clientSocket);
        echo(iOHandler);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port");
            System.out.println(e.getMessage());
        }
    }

    private static void echo(IOHandler iOHandler) throws IOException {
        String inputLine;
        while ((inputLine = iOHandler.readFromSocket()) != null) {
            iOHandler.printToSocket(inputLine);
        }
    }
}
