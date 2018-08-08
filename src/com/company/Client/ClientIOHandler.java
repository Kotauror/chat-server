package com.company.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientIOHandler {

    private final PrintWriter output;
    private final BufferedReader input;
    private final BufferedReader stdin;

    public ClientIOHandler(Socket clientSocket) throws IOException {
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.stdin = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readFromInput() throws IOException {
        return this.stdin.readLine();
    }

    public String readFromSocket() throws IOException {
        return this.input.readLine();
    }

    public void printToSocket(String inputLine)
    {
        this.output.println(inputLine);
    }

    public void printToWindow(String content) {
        System.out.println("Echo: " + content);
    }
}
