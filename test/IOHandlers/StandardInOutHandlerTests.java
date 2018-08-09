package IOHandlers;

import com.company.StandardInOutHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardInOutHandlerTests {

    private StandardInOutHandler standardInOutHandler;
    private ByteArrayOutputStream output;

    @Before
    public void setup() {
        this.output = new ByteArrayOutputStream();
        standardInOutHandler = new StandardInOutHandler(System.in, new PrintStream(output));
    }

    @Test
    public void informOfNewSocket() {
        standardInOutHandler.informOfNewSocket();

        assertTrue(output.toString().contains("A new socket has been connected"));
    }

    @Test
    public void informOfException() {
        standardInOutHandler.informOfException(3030, "Test message");

        assertTrue(output.toString().contains("Exception caught when trying to listen on port: 3030\nTest message\n"));
    }

    @Test
    public void printServerPort() {
        standardInOutHandler.printServerPort(3030);

        assertTrue(output.toString().contains("Listening on port 3030"));
    }

    @Test
    public void readsFromConsole() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("Hello".getBytes());
        StandardInOutHandler standardInOutHandlerWithInput = new StandardInOutHandler(input, new PrintStream(output));
        assertEquals("Hello", standardInOutHandlerWithInput.readFromStdIn());
    }
}
