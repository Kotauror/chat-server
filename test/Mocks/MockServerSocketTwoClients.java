package Mocks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;

public class MockServerSocketTwoClients extends ServerSocket {

    private final ByteArrayOutputStream output;
    private final ArrayList<MockSocket> clientSockets;

    public MockServerSocketTwoClients(ByteArrayOutputStream outputStream, MockSocket mockSocketOne, MockSocket mockSocketTwo) throws IOException {
        this.output = outputStream;
        this.clientSockets = new ArrayList<MockSocket>();
        fillClientSockets(mockSocketOne, mockSocketTwo);
    }

    @Override
    public MockSocket accept() {
        MockSocket firstMockSocket = (MockSocket) this.clientSockets.get(0);
        this.clientSockets.remove(0);
        return firstMockSocket;
    }

    private void fillClientSockets(MockSocket mockSocketOne, MockSocket mockSocketTwo) {
        Collections.addAll(this.clientSockets, mockSocketOne);
        Collections.addAll(this.clientSockets, mockSocketTwo);
    }
}

