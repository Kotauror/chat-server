package com.company.Server;

import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class ChatServer {

    private ArrayList<ClientThread> connectedClients;
    private int portNumber;
    private ServerSocket serverSocket;
    private StandardIOHandler standardIOHandler;
    private Executor executor;

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

    public String getClientNames() {
        StringBuilder name = new StringBuilder();
        for (ClientThread clientThread : this.connectedClients) {
            name.append(clientThread.getClientName()).append(" ");
        }
        return name.toString();
    }

    public void sendMessage(String userInput) throws IllegalAccessException {
        ClientThread clientThread = getClientThread("kot");
        clientThread.getSocketIOHandler().printToSocket("hehhegrigbewuigh");
    }

    private void connectWithClients() throws IOException {
        Socket clientSocket = this.serverSocket.accept();
        this.standardIOHandler.informOfNewClient();
        ClientThread clientThread = new ClientThread(clientSocket, this);
        addClient(clientThread);
        executor.execute(clientThread);
    }

    private void addClient(ClientThread clientThread) {
        this.connectedClients.add(clientThread);
    }

    private ClientThread getClientThread(String name) throws IllegalAccessException {
        for (ClientThread clientThread : connectedClients) {
            if (clientThread.getClientName().equals(name)) {
                return clientThread;
            }
        }
        throw new IllegalAccessException("There is no such client under this name.");
    }
}
