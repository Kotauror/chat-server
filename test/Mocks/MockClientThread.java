package Mocks;

import com.company.Server.ClientThread;

import java.io.IOException;
import java.net.Socket;

public class MockClientThread extends ClientThread {
    public MockClientThread(Socket clientSocket) throws IOException {
        super(clientSocket);
    }

    @Override
    public String getClientName() {
        return "Test name";
    }
}
