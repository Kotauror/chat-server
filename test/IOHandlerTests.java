import com.company.IOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IOHandlerTests {

    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;
    private MockSocket mockClientSocket;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream("hello".getBytes());
        mockClientSocket = new MockSocket(outputStream, inputStream);
    }

    @Test
    public void printToSocket() throws IOException {
    IOHandler iOHandler = new IOHandler(mockClientSocket);
    iOHandler.printToSocket("hello");

    assertEquals("hello", outputStream.toString().trim());
    }

    @Test
    public void readFromSocket() throws IOException {
        IOHandler iOHandler = new IOHandler(mockClientSocket);
        // iOHandler.readFromSocket();

        assertEquals("hello", iOHandler.readFromSocket());
    }
}
