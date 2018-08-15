package ClientThreadTests;

import Mocks.MockChatServer;
import Mocks.MockServerSocket;
import Mocks.MockSocket;
import com.company.Server.ClientThread;
import com.company.SocketIOHandler;
import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientThreadTests {

    private ClientThread clientThread;
    private SocketIOHandler socketIOHandler;

    @Before
    public void setup() throws IOException {
        ByteArrayOutputStream mockSocketOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream mockSocketInputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(mockSocketOutputStream, mockSocketInputStream);
        // SocketIOHandler
        socketIOHandler = new SocketIOHandler(mockClientSocket);
        // Mock Server
        MockServerSocket mockServerSocket = new MockServerSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream(), mockClientSocket);
        Executor executor = Executors.newFixedThreadPool(6);
        MockChatServer mockChatServer = new MockChatServer(mockServerSocket, new StandardIOHandler(System.in, System.out), executor);
        //Mock clientThread
        clientThread = new ClientThread(socketIOHandler, mockChatServer);
    }

    @Test
    public void setAName() throws IOException {
        clientThread.run();

        assertEquals("kot", clientThread.getClientName());
    }

    @Test
    public void returnsSocketIOHandler() throws IOException {
        clientThread.run();

        assertEquals(socketIOHandler, clientThread.getSocketIOHandler());
    }
}
