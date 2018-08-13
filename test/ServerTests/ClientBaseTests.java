package ServerTests;

import Mocks.MockClientThread;
import Mocks.MockSocket;
import com.company.Server.ClientBase;
import com.company.Server.ClientThread;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientBaseTests {

    @Test
    public void addsClientsToConnectedClients() throws IOException {
        MockClientThread mockClientThread = new MockClientThread(new MockSocket(new ByteArrayOutputStream(), new ByteArrayInputStream("".getBytes())));
        ClientBase.addClient(mockClientThread);

        assertThat(ClientBase.getConnectedClients().get(0), instanceOf(ClientThread.class));
    }

    @Test
    public void returnsNamesOfClients() throws IOException {
        MockClientThread mockClientThread = new MockClientThread(new MockSocket(new ByteArrayOutputStream(), new ByteArrayInputStream("".getBytes())));
        ClientBase.addClient(mockClientThread);

        assertEquals("Test name ", ClientBase.getClientsNames());
    }

    @Test
    public void getClientThread() throws IllegalAccessException, IOException {
        MockClientThread mockClientThread = new MockClientThread(new MockSocket(new ByteArrayOutputStream(), new ByteArrayInputStream("".getBytes())));
        ClientBase.addClient(mockClientThread);

         assertEquals(mockClientThread, ClientBase.getClientThread("Test name"));
    }
}
