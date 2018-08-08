package ClientTests;

import Mocks.MockSocket;
import com.company.Client.ClientIOHandler;
import com.company.Client.EchoClient;
import com.company.Client.StdIOHandler;
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

        StdIOHandler stdIOHandler = new StdIOHandler(mockUserInput, mockSystemOut);
        ClientIOHandler clientIOHandler = new ClientIOHandler(mockClientSocket, stdIOHandler);
        echoClient = new EchoClient(mockClientSocket, clientIOHandler);
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
