import com.company.EchoServer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoServerTest {

    private EchoServer echoServer;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("test String".getBytes());
        MockServerSocket mockServerSocket = new MockServerSocket(inputStream, outputStream);
        echoServer = new EchoServer(mockServerSocket);
    }

    @Test
    public void echo() {
        echoServer.run();
        assertEquals("test String", outputStream.toString().trim());
    }

}
