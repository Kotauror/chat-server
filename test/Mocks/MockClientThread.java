package Mocks;

import com.company.Server.ChatServer;
import com.company.Server.ClientThread;
import com.company.Server.Parser;

import java.io.IOException;
import java.net.Socket;

public class MockClientThread extends ClientThread {

    public MockClientThread(Socket clientSocket, ChatServer chatServer, Parser parser) throws IOException {
        super(clientSocket, chatServer, parser);
    }

    @Override
    public String getClientName() {
        return "Test name";
    }
}

