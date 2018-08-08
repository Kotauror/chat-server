package com.company.Client;

import java.io.IOException;
import java.net.Socket;

public class EchoClient {

    private final Socket clientSocket;
    private final ClientIOHandler clientIOHandler;
    private final StdIOHandler stdIOHandler;

    public EchoClient(Socket clientSocket, ClientIOHandler clientIOHandler, StdIOHandler stdIOHandler) {
        this.clientSocket = clientSocket;
        this.clientIOHandler = clientIOHandler;
        this.stdIOHandler = stdIOHandler;
    }

    public void run() throws IOException {
        String clientInput;
        while ((clientInput = stdIOHandler.readFromStdIn()) != null) {
            clientIOHandler.printToSocket(clientInput);
            stdIOHandler.echoToStdOut(clientIOHandler.readFromSocket());
        }
    }
}
