package com.company.Server;

import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt((args[0]));
        ServerSocket serverSocket = new ServerSocket(portNumber);
        StandardIOHandler standardIOHandler = new StandardIOHandler(System.in, System.out);
        EchoServer echoServer = new EchoServer(serverSocket, standardIOHandler);
        echoServer.run();
    }
}
