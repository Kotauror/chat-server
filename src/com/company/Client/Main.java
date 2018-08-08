package com.company.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String args[]) {
        try {
            InetAddress address = InetAddress.getByName(args[0]);
            int portNumber = Integer.parseInt((args[1]));

            Socket clientSocket = new Socket(address, portNumber);
            StdIOHandler stdIOHandler = new StdIOHandler(System.in, System.out);
            ClientIOHandler clientIOHandler = new ClientIOHandler(clientSocket);

            EchoClient echoClient = new EchoClient(clientSocket, clientIOHandler, stdIOHandler);
            echoClient.run();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
