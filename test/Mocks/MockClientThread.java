package Mocks;

import com.company.Server.ChatServer;
import com.company.Server.ClientThread;

import java.io.IOException;
import java.net.Socket;

public class MockClientThread extends ClientThread {
    public MockClientThread(MockSocket clientSocket, ChatServer chatServer) throws IOException {
        super(clientSocket, chatServer);
    }

    @Override
    public String getClientName() {
        return "Test name";
    }
}

