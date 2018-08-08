package com.company.Client;

import java.io.*;

public class StdIOHandler {

    private final PrintStream out;
    private BufferedReader stdin;

    public StdIOHandler(InputStream in, PrintStream out) {
        this.stdin = new BufferedReader(new InputStreamReader(in));
        this.out = out;
    }

    public String readFromStdIn() throws IOException {
       return stdin.readLine();
    }

    public void printToStdOut(String content) {
        this.out.println("Echo: " + content);
    }

}
