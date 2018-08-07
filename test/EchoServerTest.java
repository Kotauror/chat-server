import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoServerTest {

    private ByteArrayOutputStream outputStream;
    private MockEchoServer mockServer;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("test String".getBytes());
        MockServerSocket mockServerSocket = new MockServerSocket(inputStream, outputStream);
        mockServer = new MockEchoServer(mockServerSocket, 8080);
    }

    @Test
    public void echo() {
        mockServer.run();
        assertEquals("test String", outputStream.toString().trim());
    }

}
