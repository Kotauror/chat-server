package ServerTests;

import Mocks.MockEchoServer;
import Mocks.MockServerSocket;
import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoServerTest {

    private ByteArrayOutputStream mockOutputStream;
    private MockEchoServer mockServer;
    private ByteArrayOutputStream mockUserOutput;
    private MockServerSocket mockServerSocket;

    @Before
    public void setup() throws IOException {
        mockOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream("test String".getBytes());
        mockServerSocket = new MockServerSocket(mockInputStream, mockOutputStream);

        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("test String".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        mockServer = new MockEchoServer(mockServerSocket, standardIOHandler);
    }

    @Test
    public void printsInfoAboutListeningOnPort() {
        mockServer.run();

        assertEquals("Listening on port -1", mockUserOutput.toString().trim());
    }

    @Test
    public void oneClientTest() throws InterruptedException {
        mockServer.run();

        Thread.sleep(1000);

        assertEquals("test String", mockOutputStream.toString().trim());
    }
}
