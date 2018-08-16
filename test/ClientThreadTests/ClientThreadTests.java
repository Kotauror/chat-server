package ClientThreadTests;

import Mocks.MockChatServer;
import Mocks.MockServerSocket;
import Mocks.MockSocket;
import com.company.Server.ClientThread;
import com.company.Server.Parser;
import com.company.SocketIOHandler;
import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
    private Parser parser;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        MockServerSocket mockServerSocket = new MockServerSocket(outputStream, new MockSocket(outputStream, new ByteArrayInputStream("".getBytes())));
        Executor executor = Executors.newFixedThreadPool(2);
        parser = new Parser();
        Boolean[] shouldRunServerBooleans = {true, false};
        mockServer = new MockChatServer(mockServerSocket, new StandardIOHandler(System.in, new PrintStream(outputStream)), executor, parser, shouldRunServerBooleans);
    }

    @Test
    public void setAName() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer, parser);

        clientThread.run();

        assertEquals("kot", clientThread.getClientName());
    }

    @Test
    public void setNameDirectTest() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer, parser);
        clientThread.setClientName("test name");

        assertEquals("test name", clientThread.getClientName());
    }

    @Test
    public void returnsSocketIOHandler() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer, parser);

        clientThread.run();

        assertThat(clientThread.getSocketIOHandler(), instanceOf(SocketIOHandler.class));
    }

    @Test
    public void getUserNames() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$USERS".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer, parser);

        clientThread.run();

        assertEquals("Thread-6", clientThread.getClientName());
    }

    @Test
    public void getsConfirmationOfSendingMessage() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$MESSAGE & Thread-10 & hehe".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer, parser);
        mockServer.run();
        clientThread.run();

        assertTrue(outputStream.toString().contains("Message has been sent."));
    }

    @Test
    public void getsInfoOfNotSendingAMessage() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$MESSAGE & Thread-1 hehe".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket, mockServer, parser);
        mockServer.run();
        clientThread.run();

        assertTrue(outputStream.toString().contains("Message not sent - invalid syntax."));
    }
}
