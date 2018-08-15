package com.company.Server;

import com.company.Client.ChatClient;
import com.company.Messages;
import com.company.SocketIOHandler;
import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class ChatServer {

    private final Parser parser;
    private ArrayList<ChatClient> connectedClients;
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
        this.parser = new Parser();
    }

    public void run() {
        standardIOHandler.printToStdOut(Messages.portNumberInfo(this.portNumber));
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
        for (ChatClient chatClient : this.connectedClients) {
            name.append(chatClient.getClientName()).append(" ");
        }
        return name.toString();
    }

    public void sendMessage(String userInput) throws IllegalAccessException {
        String[] parsedUserInput = this.parser.parseMessage(userInput);
        String userName = parsedUserInput[1];
        String userMessage = parsedUserInput[2];
        ChatClient chatClient = getClientThread(userName);
        chatClient.receiveMessage(userMessage);
    }

    private void connectWithClients() throws IOException {
        Socket clientSocket = this.serverSocket.accept();
        this.standardIOHandler.printToStdOut(Messages.informOfNewClient());
        ChatClient chatClient = createNewChatClient(clientSocket);
        addClient(chatClient);
        executor.execute(chatClient.getClientThread());
    }
    private ChatClient createNewChatClient(Socket clientSocket) throws IOException {
        SocketIOHandler socketIOHandler = new SocketIOHandler(clientSocket);
        ClientThread clientThread = new ClientThread(socketIOHandler, this);
        return new ChatClient(socketIOHandler, new StandardIOHandler(System.in, System.out), clientThread);
    }

    private void addClient(ChatClient chatClient) {
        this.connectedClients.add(chatClient);
    }

    private ChatClient getClientThread(String name) throws IllegalAccessException {
        for (ChatClient chatClient : connectedClients) {
            if (chatClient.getClientName().equals(name)) {
                return chatClient;
            }
        }
        throw new IllegalAccessException("There is no such client under this name.");
    }
}
