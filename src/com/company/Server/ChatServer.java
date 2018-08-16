package com.company.Server;

import com.company.Messages;
import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class ChatServer {

    private final Parser parser;
    private ArrayList<ClientThread> connectedClients;
    private int portNumber;
    private ServerSocket serverSocket;
    private StandardIOHandler standardIOHandler;
    private Executor executor;

    public ChatServer(ServerSocket serverSocket, StandardIOHandler standardIOHandler, Executor executor, Parser parser) {
        this.executor = executor;
        this.serverSocket = serverSocket;
        this.standardIOHandler = standardIOHandler;
        this.portNumber = this.serverSocket.getLocalPort();
        this.connectedClients = new ArrayList<>();
        this.parser = parser;
    }

    public void run() {
        standardIOHandler.printToStdOut(Messages.printServerPort(this.portNumber));
        while (runServer()) {
            try {
                connectWithClients();
            } catch (IOException e) {
                this.standardIOHandler.printToStdOut("Connection with client was unsuccessful");
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

    public void sendMessage(String userInput, String senderName) throws IllegalAccessException {
        String[] parsedUserInput = this.parser.parseMessage(userInput, this.getClientNames());
        if (parsedUserInput[0].equals("error")) {
            throw new IllegalAccessException();
        } else {
            String addresseeName = parsedUserInput[1];
            String userMessage = parsedUserInput[2];
            ClientThread addresseeThread = getClientThread(addresseeName);
            String completeMessage = Parser.createMessage(senderName, userMessage);
            addresseeThread.getSocketIOHandler().printToSocket(completeMessage);
        }
    }

    private void connectWithClients() throws IOException {
        Socket clientSocket = this.serverSocket.accept();
        this.standardIOHandler.printToStdOut(Messages.newClientInServer());
        ClientThread clientThread = new ClientThread(clientSocket, this, this.parser);
        addClient(clientThread);
        executor.execute(clientThread);
    }

    private void addClient(ClientThread clientThread) {
        this.connectedClients.add(clientThread);
    }

    private ClientThread getClientThread(String name) {
        for (ClientThread clientThread : connectedClients) {
            if (clientThread.getClientName().equals(name)) {
                return clientThread;
            }
        }
        return null;
    }
}
