package ClientTests;

import Mocks.MockClientIOHandler;
import Mocks.MockSocket;
import com.company.Client.ClientIOHandler;
import com.company.Client.EchoClient;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoClientTests {

    private EchoClient echoClient;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setup() throws IOException {
        outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream socketInputStream = new ByteArrayInputStream("".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, socketInputStream);
        String endUserKeyboardInput = "testing Input";
        MockClientIOHandler mockclientIOHandler = new MockClientIOHandler(mockClientSocket, endUserKeyboardInput);

        echoClient = new EchoClient(mockClientSocket, mockclientIOHandler);
    }

    @Test
    public void echoToSocket() throws IOException {
        echoClient.run();

        assertEquals("Echo: testing Input", outputStream.toString().trim());
    }

}
