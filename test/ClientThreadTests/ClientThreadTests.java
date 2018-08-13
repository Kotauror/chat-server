package ClientThreadTests;

import Mocks.MockSocket;
import com.company.Server.ClientThread;
import com.company.SocketIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientThreadTests {

    private ByteArrayOutputStream outputStream;
    private ClientThread clientThread;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void echo() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("test String".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket);

        clientThread.run();

        assertEquals("test String", outputStream.toString().trim());
    }

    @Test
    public void setAName() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket);

        clientThread.run();

        assertEquals("kot", clientThread.getClientName());
    }

    @Test
    public void returnsSocket() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket);

        clientThread.run();

        assertEquals(mockClientSocket, clientThread.getSocket());
    }

    @Test
    public void returnsSocketIOHandler() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket);

        clientThread.run();

        assertThat(clientThread.getSocketIOHandler(), instanceOf(SocketIOHandler.class));
    }
}
