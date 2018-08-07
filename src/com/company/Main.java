package com.company;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt((args[0]));
        ServerSocket serverSocket = new ServerSocket(portNumber);
        EchoServer echoServer = new EchoServer(serverSocket, portNumber);
        echoServer.run();
    }
}
