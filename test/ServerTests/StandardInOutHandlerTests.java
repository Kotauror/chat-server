package ServerTests;

import com.company.Client.StdIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StdIOHandlerTests {

    private StdIOHandler stdIOHandler;
    private ByteArrayOutputStream output;

    @Before
    public void setup() {
        this.output = new ByteArrayOutputStream();
        stdIOHandler = new StdIOHandler(System.in, new PrintStream(output));
    }

    @Test
    public void informOfNewSocket() {
        stdIOHandler.informOfNewSocket();

        assertTrue(output.toString().contains("A new socket has been connected"));
    }

    @Test
    public void informOfException() {
        stdIOHandler.informOfException(3030, "Test message");

        assertTrue(output.toString().contains("Exception caught when trying to listen on port: 3030\nTest message\n"));
    }

    @Test
    public void printServerPort() {
        stdIOHandler.printServerPort(3030);

        assertTrue(output.toString().contains("Listening on port 3030"));
    }

    @Test
    public void readsFromConsole() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("Hello".getBytes());
        StdIOHandler stdIOHandlerWithInput = new StdIOHandler(input, new PrintStream(output));
        assertEquals("Hello", stdIOHandlerWithInput.readFromStdIn());
    }
}
