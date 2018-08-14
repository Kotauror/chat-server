package ClientThreadTests;

import Mocks.MockChatServer;
import Mocks.MockServerSocket;
import Mocks.MockSocket;
import com.company.Server.ClientThread;
import com.company.SocketIOHandler;
import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientThreadTests {

    private ByteArrayOutputStream outputStream;
    private ClientThread clientThread;
    private MockChatServer mockServer;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        MockServerSocket mockServerSocket = new MockServerSocket(inputStream, outputStream, new MockSocket(outputStream, new ByteArrayInputStream("".getBytes())));
        Executor executor = Executors.newFixedThreadPool(2);
        mockServer = new MockChatServer(mockServerSocket, new StandardIOHandler(System.in, System.out), executor);
    }

    @Test
    public void echo() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("test String".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer);

        clientThread.run();

        assertEquals("test String", outputStream.toString().trim());
    }

    @Test
    public void setAName() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer);

        clientThread.run();

        assertEquals("kot", clientThread.getClientName());
    }

    @Test
    public void returnsSocket() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer);

        clientThread.run();

        assertEquals(mockClientSocket, clientThread.getSocket());
    }

    @Test
    public void returnsSocketIOHandler() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer);

        clientThread.run();

        assertThat(clientThread.getSocketIOHandler(), instanceOf(SocketIOHandler.class));
    }
}
