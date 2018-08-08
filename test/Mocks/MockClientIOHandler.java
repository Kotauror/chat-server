package Mocks;

import com.company.Client.ClientIOHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class MockClientIOHandler extends ClientIOHandler
{
    private ArrayList strings;
    private final PrintWriter output;
    private final BufferedReader input;

    public MockClientIOHandler(Socket clientSocket, String inputString) throws IOException {
        super(clientSocket);
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.strings = new ArrayList<Boolean>();
        fillStrings(inputString, null);
    }

    private void fillStrings(String runServer, String stopServer) {
        Collections.addAll(this.strings, runServer);
        Collections.addAll(this.strings, stopServer);
    }
    public String readFromInput() throws IOException {
        String firstString = (String) this.strings.get(0);
        this.strings.remove(0);
        return firstString;
    }

    public String readFromSocket() throws IOException {
        return this.input.readLine();
    }

    public void printToSocket(String inputLine)
    {
        this.output.println("Echo: " + inputLine);
    }

    public void printToWindow(String content) { }
}
