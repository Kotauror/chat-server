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
        this.out.println("Echo: " + content);
    }

    public void informOfNewClient() {
        this.out.println("A new socket has been connected");
    }

    public void informOfException(int portNumber, String exceptionMessage) {
        this.out.println("Exception caught when trying to listen on port: " + portNumber);
        this.out.println(exceptionMessage);
    }

    public void printServerPort(int portNumber) {
        this.out.println("Listening on port " + portNumber);
    }


}
