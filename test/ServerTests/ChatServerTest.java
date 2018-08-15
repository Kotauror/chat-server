package ServerTests;

import Mocks.MockChatServer;
import Mocks.MockServerSocket;
import Mocks.MockServerSocketTwoClients;
import Mocks.MockSocket;
import com.company.Server.CurrentThreadExecutor;
import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatServerTest {

    private ByteArrayOutputStream mockOutputStream;
    private MockChatServer mockServer;
    private ByteArrayOutputStream mockUserOutput;
    private MockServerSocket mockServerSocket;

    @Before
    public void setup() throws IOException {
        mockOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream("test String".getBytes());
        MockSocket mockSocket = new MockSocket(mockOutputStream, mockInputStream);

        mockServerSocket = new MockServerSocket(mockInputStream, mockOutputStream, mockSocket);

        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("test String".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        Executor executor = new CurrentThreadExecutor();

        mockServer = new MockChatServer(mockServerSocket, standardIOHandler, executor);
    }

    @Test
    public void printsInfoAboutListeningOnPortAndNewConnection() {
        mockServer.run();

        assertEquals("Listening on port -1\nA new socket has been connected", mockUserOutput.toString().trim());
    }

    @Test
    public void returnsNamesOfClients() throws InterruptedException {
        mockServer.run();

        Thread.sleep(1000);

        assertEquals("Thread-9 ", mockServer.getClientNames());
    }

    @Test
    public void messageIsSendToSocket() throws IOException {
        // Client 1
        ByteArrayOutputStream mockOutputStreamClientOne = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStreamClientOne = new ByteArrayInputStream("$MESSAGE & Thread-2 & Hello".getBytes());
        MockSocket mockSocketOne = new MockSocket(mockOutputStreamClientOne, mockInputStreamClientOne);

        // Client 2
        ByteArrayOutputStream mockOutputStreamClientTwo = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStreamClientTwo = new ByteArrayInputStream("$MESSAGE & Thread-2 & Hello".getBytes());
        MockSocket mockSocketTwo = new MockSocket(mockOutputStreamClientTwo, mockInputStreamClientTwo);

        // ServerSocket
        MockServerSocketTwoClients mockServerSocketTwoClients = new MockServerSocketTwoClients(new ByteArrayInputStream("$MESSAGE & Thread-2 & Hello".getBytes()), mockOutputStream, mockSocketOne, mockSocketTwo);

        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        Executor executor = new CurrentThreadExecutor();

        mockServer = new MockChatServer(mockServerSocketTwoClients, standardIOHandler, executor);

        mockServer.run();

        assertEquals("$MESSAGE & Thread-2 & Hello", new BufferedReader(new InputStreamReader(mockSocketTwo.getInputStream())).readLine());
    }
}
