package IOHandlers;

import Mocks.MockSocket;
import com.company.SocketIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocketIOHandlerTests {

    private ByteArrayOutputStream outputStream;
    private MockSocket mockClientSocket;

    @Before
    public void setup() {
        outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("hello".getBytes());
        mockClientSocket = new MockSocket(outputStream, inputStream);
    }

    @Test
    public void printToSocket() throws IOException {
    SocketIOHandler iOHandlerSocket = new SocketIOHandler(mockClientSocket);
    iOHandlerSocket.printToSocket("hello");

    assertEquals("hello", outputStream.toString().trim());
    }

    @Test
    public void readFromSocket() throws IOException {
        SocketIOHandler iOHandlerSocket = new SocketIOHandler(mockClientSocket);

        assertEquals("hello", iOHandlerSocket.readFromSocket());
    }
}
