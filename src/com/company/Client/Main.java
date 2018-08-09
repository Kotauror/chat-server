package com.company.Client;

import com.company.SocketIOHandler;
import com.company.StandardIOHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String args[]) {
        try {
            InetAddress address = InetAddress.getByName(args[0]);
            int portNumber = Integer.parseInt((args[1]));

            Socket clientSocket = new Socket(address, portNumber);
            SocketIOHandler socketIOHandler = new SocketIOHandler(clientSocket);
            StandardIOHandler standardIOHandler = new StandardIOHandler(System.in, System.out);

            EchoClient echoClient = new EchoClient(clientSocket, socketIOHandler, standardIOHandler);
            echoClient.run();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
