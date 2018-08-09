package ClientTests;

import Mocks.MockSocket;
import com.company.Client.EchoClient;
import com.company.StandardIOHandler;
import com.company.SocketIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoClientTests {

    private EchoClient echoClient;
    private ByteArrayOutputStream mockSocketOutputStream;
    private ByteArrayOutputStream mockUserOutput;
    private ByteArrayInputStream mockSocketInputStream;
    private ByteArrayInputStream mockUserInput;

    @Before
    public void setup() throws IOException {
        mockSocketOutputStream = new ByteArrayOutputStream();
        mockSocketInputStream = new ByteArrayInputStream("hello".getBytes());
        MockSocket mockClientSocket = new MockSocket(mockSocketOutputStream, mockSocketInputStream);

        mockUserInput = new ByteArrayInputStream("hello".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);

        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);
        SocketIOHandler clientSocketIOHandler = new SocketIOHandler(mockClientSocket);
        echoClient = new EchoClient(mockClientSocket, clientSocketIOHandler, standardIOHandler);
    }

    @Test
    public void echosToSocket() throws IOException {
        echoClient.run();

        assertEquals("hello", mockSocketOutputStream.toString().trim());
    }

    @Test
    public void echosToUsersScreen() throws IOException {
        echoClient.run();

        assertEquals("Echo: hello", mockUserOutput.toString().trim());
    }
}
