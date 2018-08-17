package ClientTests;

import Mocks.MockSocket;
import com.company.Client.ChatClient;
import com.company.Server.CurrentThreadExecutor;
import com.company.StandardIOHandler;
import com.company.SocketIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatClientTests {

    private ChatClient chatClient;
    private ByteArrayOutputStream mockSocketOutputStream;
    private ByteArrayOutputStream mockUserOutput;

    @Before
    public void setup() throws IOException {
        // SocketIO
        mockSocketOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream mockSocketInputStream = new ByteArrayInputStream("test".getBytes());
        MockSocket mockClientSocket = new MockSocket(mockSocketOutputStream, mockSocketInputStream);
        SocketIOHandler clientSocketIOHandler = new SocketIOHandler(mockClientSocket);

        // StandardIO
        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("hello".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        CurrentThreadExecutor currentThreadExecutor = new CurrentThreadExecutor();

        chatClient = new ChatClient(clientSocketIOHandler, standardIOHandler, currentThreadExecutor);
    }

    @Test
    public void listenForPrompts() {
        chatClient.run();

        assertEquals("hello", mockSocketOutputStream.toString().trim());
    }

    @Test
    public void listenForMessages() {
        chatClient.run();

        assertEquals("test", mockUserOutput.toString().trim());
    }
}
