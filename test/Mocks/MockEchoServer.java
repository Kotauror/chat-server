package Mocks;

import com.company.StandardIOHandler;
import com.company.Server.EchoServer;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;

public class MockEchoServer extends EchoServer {

    private ArrayList runServerBooleans;

    public MockEchoServer(ServerSocket serverSocket, StandardIOHandler standardIOHandler, Executor executor) {
        super(serverSocket, standardIOHandler, executor);
        this.runServerBooleans = new ArrayList<Boolean>();
        fillBooleans(true, false);
    }
    
    private void fillBooleans(boolean runServer, boolean stopServer) {
        Collections.addAll(this.runServerBooleans, runServer);
        Collections.addAll(this.runServerBooleans, stopServer);
    }

    public boolean runServer() {
        Boolean firstBoolean = (Boolean) this.runServerBooleans.get(0);
        this.runServerBooleans.remove(0);
        return firstBoolean;
    }
}
