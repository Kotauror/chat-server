package ClientTests;

import Mocks.MockChatServer;
import Mocks.MockServerSocket;
import Mocks.MockSocket;
import com.company.Client.ChatClient;
import com.company.Server.ClientThread;
import com.company.StandardIOHandler;
import com.company.SocketIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatClientTests {

    private ChatClient chatClient;
    private ByteArrayOutputStream mockSocketOutputStream;
    private ByteArrayOutputStream mockUserOutput;

    @Before
    public void setup() throws IOException {
        // Mock client's Socket
        mockSocketOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream mockSocketInputStream = new ByteArrayInputStream("".getBytes());
        MockSocket mockClientSocket = new MockSocket(mockSocketOutputStream, mockSocketInputStream);
        // SocketIOHandler
        SocketIOHandler socketIOHandler = new SocketIOHandler(mockClientSocket);
        // Mock Server
        MockServerSocket mockServerSocket = new MockServerSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream(), mockClientSocket);
        Executor executor = Executors.newFixedThreadPool(6);
        MockChatServer mockChatServer = new MockChatServer(mockServerSocket, new StandardIOHandler(System.in, System.out), executor);
        //Mock clientThread
        ClientThread clientThread = new ClientThread(socketIOHandler, mockChatServer);
        // StandardIOHandler
        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("hello".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        chatClient = new ChatClient(socketIOHandler, standardIOHandler, clientThread);
    }

    @Test
    public void getsStringFromKeyboardToSocketOutput() throws IOException, InterruptedException {
        chatClient.run();

        Thread.sleep(100);

        assertEquals("hello", mockSocketOutputStream.toString().trim());
    }

    @Test
    public void receiveMessage() throws InterruptedException {
        chatClient.receiveMessage("test message");

        Thread.sleep(100);

        assertEquals("test message", mockSocketOutputStream.toString().trim());
    }

    @Test
    public void getClientName() {
        assertEquals("Thread-5", chatClient.getClientName());
    }
}
