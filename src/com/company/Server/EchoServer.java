package com.company.Server;

import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

public class EchoServer {

    private final int portNumber;
    private ServerSocket serverSocket;
    private StandardIOHandler standardIOHandler;
    private Executor executor;

    public EchoServer(ServerSocket serverSocket, StandardIOHandler standardIOHandler, Executor executor) {
        this.executor = executor;
        this.serverSocket = serverSocket;
        this.standardIOHandler = standardIOHandler;
        this.portNumber = this.serverSocket.getLocalPort();
    }

    public void run() {
        standardIOHandler.printServerPort(this.portNumber);
        while (runServer()) {
            try {
                connectWithClients();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean runServer() {
        return true;
    }

    private void connectWithClients() throws IOException {
        Socket clientSocket = this.serverSocket.accept();
        this.standardIOHandler.informOfNewClient();
        Thread clientThread = new ClientThread(clientSocket);
        executor.execute(clientThread);
    }
}
