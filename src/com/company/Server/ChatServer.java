package com.company.Server;

import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class ChatServer {

    private int portNumber;
    private ServerSocket serverSocket;
    private StandardIOHandler standardIOHandler;
    private Executor executor;
    private ArrayList<Runnable> connectedClients;

    public ChatServer(ServerSocket serverSocket, StandardIOHandler standardIOHandler, Executor executor) {
        this.executor = executor;
        this.serverSocket = serverSocket;
        this.standardIOHandler = standardIOHandler;
        this.portNumber = this.serverSocket.getLocalPort();
        this.connectedClients = new ArrayList<>();
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

    public ArrayList getConnectedClients() {
        return this.connectedClients;
    }

    private void connectWithClients() throws IOException {
        Socket clientSocket = this.serverSocket.accept();
        this.standardIOHandler.informOfNewClient();
        Runnable clientThread = new ClientThread(clientSocket);
        connectedClients.add(clientThread);
        executor.execute(clientThread);
    }
}
