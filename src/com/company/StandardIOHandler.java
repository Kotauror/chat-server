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

    public void printToStdOut(String content) {
        this.out.println(content);
    }
}
