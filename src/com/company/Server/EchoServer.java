package com.company.Server;

import com.company.Messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private ServerSocket serverSocket;
    private int portNumber;
    private Messenger messenger;

    public EchoServer(ServerSocket serverSocket, int portNumber, Messenger messenger) {
        this.serverSocket = serverSocket;
        this.portNumber = portNumber;
        this.messenger = messenger;
    }

    public void run() {
        messenger.printServerPort(portNumber);
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
            messenger.informOfNewSocket();
            echo(clientSocket);
        } catch (IOException exception) {
            messenger.informOfException(this.portNumber, exception.getMessage());
        }
    }

    private Socket accept() throws IOException {
        return this.serverSocket.accept();
    }

    private static void echo(Socket clientSocket) throws IOException {
        SocketIOHandler iOHandlerSocket = new SocketIOHandler(clientSocket);
        String inputLine;
        while ((inputLine = iOHandlerSocket.readFromSocket()) != null) {
                iOHandlerSocket.printToSocket(inputLine);
        }
    }
}
