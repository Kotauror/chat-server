package Mocks;

import com.company.Client.ClientIOHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MockClientIOHandler extends ClientIOHandler
{
    private ArrayList endUserInputStrings;
    private final PrintWriter output;

    public MockClientIOHandler(Socket clientSocket, String inputString) throws IOException {
        super(clientSocket);
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.endUserInputStrings = new ArrayList<String>();
        endUserInputStrings.add(inputString);
        endUserInputStrings.add(null);
    }

    public String readFromInput() throws IOException {
        String firstString = (String) this.endUserInputStrings.get(0);
        this.endUserInputStrings.remove(0);
        return firstString;
    }

    public void printToSocket(String inputLine)
    {
        this.output.println("Echo: " + inputLine);
    }
}
