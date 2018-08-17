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

import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientThreadTests {

    private ByteArrayOutputStream outputStream;
    private MockChatServer mockServer;
    private Parser parser;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
        MockServerSocket mockServerSocket = new MockServerSocket(outputStream, new MockSocket(outputStream, new ByteArrayInputStream("".getBytes())));
        Executor executor = Executors.newFixedThreadPool(2);
        parser = new Parser();
        Boolean[] shouldRunServerBooleans = {true, false};
        mockServer = new MockChatServer(mockServerSocket, new StandardIOHandler(System.in, new PrintStream(outputStream)), executor, parser, shouldRunServerBooleans);
    }

    @Test
    public void setAName() throws IOException {
        ClientThread clientThread = createClientThread("$NAME kot");

        clientThread.run();

        assertEquals("kot", clientThread.getClientName());
    }

    @Test
    public void setNameDirectTest() throws IOException {
        ClientThread clientThread = createClientThread("$NAME kot");

        clientThread.setClientName("test name");

        assertEquals("test name", clientThread.getClientName());
    }

    @Test
    public void returnsSocketIOHandler() throws IOException {
        ClientThread clientThread = createClientThread("$NAME kot");

        clientThread.run();

        assertThat(clientThread.getSocketIOHandler(), instanceOf(SocketIOHandler.class));
    }

    @Test
    public void getUserNames() throws IOException {
        ClientThread clientThread = createClientThread("$USERS");

        clientThread.run();

        assertEquals("Thread-8", clientThread.getClientName());
    }

    @Test
    public void getsConfirmationOfSendingMessage() throws IOException {
        ClientThread clientThread = createClientThreadWithOutputStream("$MESSAGE & Thread-14 & hehe", outputStream);

        mockServer.run();
        clientThread.run();

        assertTrue(outputStream.toString().contains("Message has been sent."));
    }

    @Test
    public void getsInfoOfNotSendingAMessage() throws IOException {
        ClientThread clientThread = createClientThreadWithOutputStream("$MESSAGE & Thread-1 hehe", outputStream);

        mockServer.run();
        clientThread.run();

        assertTrue(outputStream.toString().contains("Message not sent - invalid syntax."));
    }

    @Test
    public void userLearnsAboutCreatingARoom() throws IOException, IllegalAccessException {
        ClientThread clientThread = createClientThreadWithOutputStream("$NEW_ROOM kotek", outputStream);

        mockServer.run();
        clientThread.run();

        assertTrue(outputStream.toString().contains("New Room has been created"));
    }

    @Test
    public void userLearnsAboutNotCreatingARoom() throws IOException, IllegalAccessException {
        ClientThread clientThread = createClientThreadWithOutputStream("$NEW_ROOM", outputStream);

        mockServer.run();
        clientThread.run();

        assertTrue(outputStream.toString().contains("Failure in creating a new room"));
    }

    @Test
    public void userAsksForAvailableRooms() throws IOException, IllegalAccessException {
        ClientThread clientThread = createClientThreadWithOutputStream("$ROOMS", outputStream);

        mockServer.run();
        clientThread.run();

        assertTrue(outputStream.toString().contains("There are following rooms: "));
    }

    private ClientThread createClientThread(String input) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        return new ClientThread(mockClientSocket, mockServer, parser);
    }

    private ClientThread createClientThreadWithOutputStream(String input, ByteArrayOutputStream outputStream) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        return new ClientThread(mockClientSocket, mockServer, parser);
    }

}
