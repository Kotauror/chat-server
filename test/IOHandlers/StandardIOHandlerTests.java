package IOHandlers;

import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardIOHandlerTests {

    private StandardIOHandler standardIOHandler;
    private ByteArrayOutputStream output;

    @Before
    public void setup() {
        this.output = new ByteArrayOutputStream();
        standardIOHandler = new StandardIOHandler(System.in, new PrintStream(output));
    }

    @Test
    public void informOfNewSocket() {
        standardIOHandler.informOfNewSocket();

        assertTrue(output.toString().contains("A new socket has been connected"));
    }

    @Test
    public void informOfException() {
        standardIOHandler.informOfException(3030, "Test message");

        assertTrue(output.toString().contains("Exception caught when trying to listen on port: 3030\nTest message\n"));
    }

    @Test
    public void printServerPort() {
        standardIOHandler.printServerPort(3030);

        assertTrue(output.toString().contains("Listening on port 3030"));
    }

    @Test
    public void readsFromConsole() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("Hello".getBytes());
        StandardIOHandler standardIOHandlerWithInput = new StandardIOHandler(input, new PrintStream(output));
        assertEquals("Hello", standardIOHandlerWithInput.readFromStdIn());
    }
}
