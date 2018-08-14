package ServerTests;

import Mocks.MockClientThread;
import Mocks.MockSocket;
import com.company.Server.ClientConnectionsStore;
import com.company.Server.ClientThread;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientConnectionsStoreTests {

    @Test
    public void addsClientsToConnectedClients() throws IOException {
        MockClientThread mockClientThread = new MockClientThread(new MockSocket(new ByteArrayOutputStream(), new ByteArrayInputStream("".getBytes())));
        ClientConnectionsStore.addClient(mockClientThread);

        assertThat(ClientConnectionsStore.getConnectedClients().get(0), instanceOf(ClientThread.class));
    }

    @Test
    public void returnsNamesOfClients() throws IOException, IllegalAccessException, NoSuchFieldException {
        clearState();
        MockClientThread mockClientThread = new MockClientThread(new MockSocket(new ByteArrayOutputStream(), new ByteArrayInputStream("".getBytes())));
        ClientConnectionsStore.addClient(mockClientThread);

        assertEquals("Test name ", ClientConnectionsStore.getClientsNames());
    }

    @Test
    public void getClientThread() throws IllegalAccessException, IOException, NoSuchFieldException {
        clearState();
        MockClientThread mockClientThread = new MockClientThread(new MockSocket(new ByteArrayOutputStream(), new ByteArrayInputStream("".getBytes())));
        ClientConnectionsStore.addClient(mockClientThread);

        assertEquals(mockClientThread, ClientConnectionsStore.getClientThread("Test name"));
    }

    private void clearState() throws NoSuchFieldException, IllegalAccessException {
        Field field = ClientConnectionsStore.class.getDeclaredField("connectedClients");
        field.setAccessible(true);
        field.set(null, new ArrayList<>());
    }
}
