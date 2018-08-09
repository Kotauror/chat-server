package com.company.Server;

import com.company.StandardInOutHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt((args[0]));
        ServerSocket serverSocket = new ServerSocket(portNumber);
        StandardInOutHandler standardInOutHandler = new StandardInOutHandler(System.in, System.out);
        EchoServer echoServer = new EchoServer(serverSocket, standardInOutHandler);
        echoServer.run();
    }
}
