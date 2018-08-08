package com.company.Client;

import java.io.IOException;
import java.net.Socket;

public class EchoClient {

    private final Socket clientSocket;
    private final ClientIOHandler clientIOHandler;

    public EchoClient(Socket clientSocket, ClientIOHandler clientIOHandler) {
        this.clientSocket = clientSocket;
        this.clientIOHandler = clientIOHandler;
    }

    public void run() throws IOException {
        String clientInput;
        while ((clientInput = clientIOHandler.readFromInput()) != null) {
            clientIOHandler.printToSocket(clientInput);
            clientIOHandler.printToWindow(clientIOHandler.readFromSocket());
        }
    }
}
