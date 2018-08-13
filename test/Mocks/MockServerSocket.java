package Mocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class MockServerSocket extends ServerSocket {

    private final ByteArrayInputStream input;
    private final ByteArrayOutputStream output;
    private final MockSocket mockSocket;

    public MockServerSocket(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream, MockSocket mockSocket) throws IOException {
        this.input = inputStream;
        this.output = outputStream;
        this.mockSocket = mockSocket;
    }

    @Override
    public MockSocket accept() {
        return this.mockSocket;
    }
}

