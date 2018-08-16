package ServerTests;

import Mocks.MockChatServer;
import Mocks.MockServerSocket;
import Mocks.MockServerSocketTwoClients;
import Mocks.MockSocket;
import com.company.Server.CurrentThreadExecutor;
import com.company.Server.Parser;
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

        mockServerSocket = new MockServerSocket(mockOutputStream, mockSocket);

        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("test String".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        Executor executor = new CurrentThreadExecutor();
        Parser parser = new Parser();
        Boolean[] shouldRunServerBooleans = {true, false};
        mockServer = new MockChatServer(mockServerSocket, standardIOHandler, executor, parser, shouldRunServerBooleans);
    }

    @Test
    public void printsInfoAboutListeningOnPortAndNewConnection() {
        mockServer.run();

        assertEquals("Listening on port -1\nA new socket has been connected", mockUserOutput.toString().trim());
    }

    @Test
    public void returnsNamesOfClients() {
        mockServer.run();

        assertEquals("Thread-14 ", mockServer.getClientNames());
    }

    @Test(expected= IllegalAccessException.class)
    public void sendMessageThrowsErrorOnInvalidMessage() throws IllegalAccessException {
        mockServer.sendMessage("admin", "test invalid string");
    }

    @Test
    public void messageIsSendToSocket() throws IOException {
        // Client 1
        ByteArrayOutputStream mockOutputStreamClientOne = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStreamClientOne = new ByteArrayInputStream("$MESSAGE & Thread-15 & Hello".getBytes());
        MockSocket mockSocketOne = new MockSocket(mockOutputStreamClientOne, mockInputStreamClientOne);

        // Client 2
        ByteArrayOutputStream mockOutputStreamClientTwo = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStreamClientTwo = new ByteArrayInputStream("".getBytes());
        MockSocket mockSocketTwo = new MockSocket(mockOutputStreamClientTwo, mockInputStreamClientTwo);

        // ServerSocket
        ByteArrayOutputStream mockServerOutput = new ByteArrayOutputStream();
        MockServerSocketTwoClients mockServerSocketTwoClients = new MockServerSocketTwoClients(mockServerOutput, mockSocketOne, mockSocketTwo);

        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("$MESSAGE & Thread-15 & Hello".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        Executor executor = new CurrentThreadExecutor();
        Parser parser = new Parser();
        Boolean[] shouldRunServerBooleans = {true, true, false};
        mockServer = new MockChatServer(mockServerSocketTwoClients, standardIOHandler, executor, parser, shouldRunServerBooleans);

        mockServer.run();

        assertEquals("Connected to a server\n" +
                        "---------- INSTRUCTIONS ----------\n" +
                "> To set your username, type $NAME username\n" +
                "> To see the list od users, type $USERS\n" +
                "> To send a message type $MESSAGE & UserNameOfAddressee & your message\n" +
                "----------------------------------\n\n" +
                "💬  Thread-15: Hello\n" +
                "Message has been sent.", mockOutputStreamClientOne.toString().trim());
    }
}
