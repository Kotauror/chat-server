package com.company.Client;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String args[]) throws IOException {
        try {
            InetAddress address = InetAddress.getByName(args[0]);
            int portNumber = Integer.parseInt((args[1]));
            Socket clientSocket = new Socket(address, portNumber);
            ClientIOHandler clientIOHandler = new ClientIOHandler(clientSocket);
            EchoClient echoClient = new EchoClient(clientSocket, clientIOHandler);
            echoClient.run();
        } catch (IOException exception) {
//                .informOfException(this.portNumber, exception.getMessage());
        }
    }
}
