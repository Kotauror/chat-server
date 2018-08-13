package com.company;

import java.io.*;

public class StandardIOHandler {

    private final PrintStream out;
    private BufferedReader stdin;

    public StandardIOHandler(InputStream in, PrintStream out) {
        this.stdin = new BufferedReader(new InputStreamReader(in));
        this.out = out;
    }

    public String readFromStdIn() throws IOException {
       return stdin.readLine();
    }

    public void echoToStdOut(String content) {
        this.out.println(content);
    }

    public void informOfNewClient() {
        this.out.println("A new socket has been connected");
    }

    public void informOfException(int portNumber, String exceptionMessage) {
        this.out.println("Exception caught when trying to listen on port: " + portNumber);
        this.out.println(exceptionMessage);
    }

    public void informOfConnectionToServer() {
        this.out.println("Connected to a server");
    }

    public void printServerPort(int portNumber) {
        this.out.println("Listening on port " + portNumber);
    }

    public void informOfRules() {
        this.out.println("To set your username, type $NAME: and your username after colon\n" +
                "To see the list od users, type $USERS\n" +
                "To send a message type $MESSAGE_UserName: message");
    }
}
