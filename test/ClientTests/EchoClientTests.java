package ClientTests;

import Mocks.MockSocket;
import com.company.Client.ClientIOHandler;
import com.company.Client.EchoClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoClientTests {

    private EchoClient echoClient;
//
//    @Before
//    public void setup() throws IOException {
//        MockClientSocket mockClientSocket = new MockClientSocket(inputStream, outputStream);
//        ClientIOHandler clientIOHandler = new ClientIOHandler(clientSocket);
//        echoClient = new EchoClient(clientSocket, clientIOHandler);
//    }
//
//    @Test
//    public void echoAllowsToReadInutedTest() throws IOException {
//        echoClient.run();
//
//        assertEquals("test String", outputStream.toString().trim());
//    }

}
