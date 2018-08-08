package ServerTests;

import com.company.Server.ServerMessenger;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;

public class ServerMessengerTests {

    private ServerMessenger serverMessenger;
    private ByteArrayOutputStream output;

    @Before
    public void setup() {
        this.output = new ByteArrayOutputStream();
        serverMessenger = new ServerMessenger(new PrintStream(output));
    }

    @Test
    public void informOfNewSocket() {
        serverMessenger.informOfNewSocket();

        assertTrue(output.toString().contains("A new socket has been connected"));
    }

    @Test
    public void informOfException() {
        serverMessenger.informOfException(3030, "Test message");

        assertTrue(output.toString().contains("Exception caught when trying to listen on port: 3030\nTest message\n"));
    }

    @Test
    public void printServerPort() {
        serverMessenger.printServerPort(3030);

        assertTrue(output.toString().contains("Listening on port 3030"));
    }
}
