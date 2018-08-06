package com.company;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        EchoServer echoserver = new EchoServer(serverSocket);
        echoserver.run();
    }
}
