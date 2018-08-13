package com.company.Server;

import java.util.ArrayList;

public class ClientBase {

    static {
        connectedClients = new ArrayList<>();
    }

    private static ArrayList<ClientThread> connectedClients;

    public static void addClient(ClientThread clientThread) {
        connectedClients.add(clientThread);
    }

    public static ArrayList<ClientThread> getConnectedClients() {
        return connectedClients;
    }

    public static String getClientsNames() {
        StringBuilder name = new StringBuilder();
        for (ClientThread clientThread : connectedClients) {
            name.append(clientThread.getClientName()).append(" ");
        }
        return name.toString();
    }
}
