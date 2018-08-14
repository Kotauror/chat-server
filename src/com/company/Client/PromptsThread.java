package com.company.Client;

import com.company.SocketIOHandler;
import com.company.StandardIOHandler;

import java.io.IOException;

public class PromptsThread extends Thread {

    private final StandardIOHandler standardIOHandler;
    private final SocketIOHandler socketIOHandler;

    public PromptsThread(StandardIOHandler standardIOHandler, SocketIOHandler socketIOHandler) {
        this.standardIOHandler = standardIOHandler;
        this.socketIOHandler = socketIOHandler;

    }

    @Override
    public void run() {
        try {
            listenForPrompts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForPrompts() throws IOException {
        String clientInput;
        while ((clientInput = standardIOHandler.readFromStdIn()) != null) {
            socketIOHandler.printToSocket(clientInput);
        }
    }
}
