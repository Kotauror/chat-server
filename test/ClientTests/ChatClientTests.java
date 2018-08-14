package ClientTests;

import Mocks.MockSocket;
import com.company.Client.ChatClient;
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
        mockSocketOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream mockSocketInputStream = new ByteArrayInputStream("".getBytes());
        MockSocket mockClientSocket = new MockSocket(mockSocketOutputStream, mockSocketInputStream);

        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("hello".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);

        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);
        SocketIOHandler clientSocketIOHandler = new SocketIOHandler(mockClientSocket);
        chatClient = new ChatClient(mockClientSocket, clientSocketIOHandler, standardIOHandler);
    }

    @Test
    public void getsStringFromKeyboardToSocketOutput() throws IOException, InterruptedException {
        chatClient.run();

        Thread.sleep(100);

        assertEquals("hello", mockSocketOutputStream.toString().trim());
    }

    @Test
    public void informsOfConnectionAndEchosToUsersScreen() throws IOException {
        chatClient.run();

        assertEquals("Connected to a server\n" +
                "To set your username, type $NAME: and your username after colon\n" +
                "To see the list od users, type $USERS\n" +
                "To send a message type $MESSAGE & UserNameOfAddressee & Here goes your message", mockUserOutput.toString().trim());
    }
}
