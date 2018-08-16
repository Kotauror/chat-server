package Mocks;

import com.company.Server.Parser;
import com.company.StandardIOHandler;
import com.company.Server.ChatServer;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;

public class MockChatServer extends ChatServer {

    private ArrayList runServerBooleans;

    public MockChatServer(ServerSocket serverSocket, StandardIOHandler standardIOHandler, Executor executor, Parser parser, Boolean[] shouldRunServerBooleans) {
        super(serverSocket, standardIOHandler, executor, parser);
        this.runServerBooleans = new ArrayList<Boolean>();
        fillBooleans(shouldRunServerBooleans);
    }
    
    private void fillBooleans(Boolean[] shouldRunServerBooleans) {
        for (Boolean shouldRunServerBoolean : shouldRunServerBooleans) {
            Collections.addAll(this.runServerBooleans, shouldRunServerBoolean);
        }
    }

    public boolean runServer() {
        Boolean firstBoolean = (Boolean) this.runServerBooleans.get(0);
        this.runServerBooleans.remove(0);
        return firstBoolean;
    }
}
