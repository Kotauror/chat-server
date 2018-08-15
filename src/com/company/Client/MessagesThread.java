package com.company.Client;

import com.company.SocketIOHandler;
import com.company.StandardIOHandler;

import java.io.IOException;

public class MessagesThread extends Thread {

    private final StandardIOHandler standardIOHandler;
    private final SocketIOHandler socketIOHandler;

    public MessagesThread(StandardIOHandler standardIOHandler, SocketIOHandler socketIOHandler) {
        this.standardIOHandler = standardIOHandler;
        this.socketIOHandler = socketIOHandler;
    }

    @Override
    public void run() {
        try {
            listenForMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForMessages() throws IOException {
        String message;
        while ((message = socketIOHandler.readFromSocket()) != null) {
            standardIOHandler.printToStdOut(message);
        }
    }

}
