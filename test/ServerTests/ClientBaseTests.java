package ServerTests;

import Mocks.MockClientThread;
import com.company.Server.ClientBase;
import com.company.Server.ClientThread;
import com.sun.security.ntlm.Client;
import org.junit.Test;

import java.net.Socket;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientBaseTests {

    @Test
    public void addsClientsToConnectedClients() {
        MockClientThread mockClientThread = new MockClientThread(new Socket());
        ClientBase.addClient(mockClientThread);

        assertThat(ClientBase.getConnectedClients().get(0), instanceOf(ClientThread.class));
    }

    @Test
    public void returnsNamesOfClients() {
        MockClientThread mockClientThread = new MockClientThread(new Socket());
        ClientBase.addClient(mockClientThread);

        assertEquals("null null Test name ", ClientBase.getClientsNames());
    }



}
