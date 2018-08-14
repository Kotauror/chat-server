package ServerTests;

import Mocks.MockChatServer;
import Mocks.MockClientThread;
import Mocks.MockServerSocket;
import Mocks.MockSocket;
import com.company.Server.ClientConnectionsStore;
import com.company.Server.ClientThread;
import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientConnectionsStoreTests {

    private MockClientThread mockClientThread;

    @Before
    public void setup() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        MockServerSocket mockServerSocket = new MockServerSocket(inputStream, outputStream, new MockSocket(outputStream, new ByteArrayInputStream("".getBytes())));
        Executor executor = Executors.newFixedThreadPool(2);
        MockChatServer mockChatServer = new MockChatServer(mockServerSocket, new StandardIOHandler(System.in, System.out), executor);
        mockClientThread = new MockClientThread(new MockSocket(new ByteArrayOutputStream(), new ByteArrayInputStream("".getBytes())), mockChatServer);
    }


    @Test
    public void addsClientsToConnectedClients() throws IOException {
        ClientConnectionsStore.addClient(mockClientThread);

        assertThat(ClientConnectionsStore.getConnectedClients().get(0), instanceOf(ClientThread.class));
    }

    @Test
    public void returnsNamesOfClients() throws IOException, IllegalAccessException, NoSuchFieldException {
        clearState();
        ClientConnectionsStore.addClient(mockClientThread);

        assertEquals("Test name ", ClientConnectionsStore.getClientsNames());
    }

    @Test
    public void getClientThread() throws IllegalAccessException, IOException, NoSuchFieldException {
        clearState();
        ClientConnectionsStore.addClient(mockClientThread);

        assertEquals(mockClientThread, ClientConnectionsStore.getClientThread("Test name"));
    }

    private void clearState() throws NoSuchFieldException, IllegalAccessException {
        Field field = ClientConnectionsStore.class.getDeclaredField("connectedClients");
        field.setAccessible(true);
        field.set(null, new ArrayList<>());
    }
}
