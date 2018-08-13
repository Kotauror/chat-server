package ClientThreadTests;

import Mocks.MockSocket;
import com.company.Server.ClientThread;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientThreadTests {

    private ByteArrayOutputStream outputStream;
    private ClientThread clientThread;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void echo() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("test String".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket);

        clientThread.run();

        assertEquals("test String", outputStream.toString().trim());
    }

    @Test
    public void setAName() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket);

        clientThread.run();

        assertEquals("kot", clientThread.getClientName());
    }

    @Test
    public void returnsSocket() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$NAME:kot".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        clientThread = new ClientThread(mockClientSocket);

        clientThread.run();

        assertEquals(mockClientSocket, clientThread.getSocket());
    }
}
