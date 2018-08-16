package Mocks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class MockServerSocket extends ServerSocket {

    private final ByteArrayOutputStream output;
    private final MockSocket mockSocket;

    public MockServerSocket(ByteArrayOutputStream outputStream, MockSocket mockSocket) throws IOException {
        this.output = outputStream;
        this.mockSocket = mockSocket;
    }

    @Override
    public MockSocket accept() {
        return this.mockSocket;
    }
}

