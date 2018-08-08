package ServerTests;

import com.company.Messenger;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;

public class MessengerTests {

    private Messenger messenger;
    private ByteArrayOutputStream output;

    @Before
    public void setup() {
        this.output = new ByteArrayOutputStream();
        messenger = new Messenger(new PrintStream(output));
    }

    @Test
    public void informOfNewSocket() {
        messenger.informOfNewSocket();

        assertTrue(output.toString().contains("A new socket has been connected"));
    }

    @Test
    public void informOfException() {
        messenger.informOfException(3030, "Test message");

        assertTrue(output.toString().contains("Exception caught when trying to listen on port: 3030\nTest message\n"));
    }

    @Test
    public void printServerPort() {
        messenger.printServerPort(3030);

        assertTrue(output.toString().contains("Listening on port 3030"));
    }
}
