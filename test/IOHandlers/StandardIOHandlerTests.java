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
    public void readsFromConsole() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("Hello".getBytes());
        StandardIOHandler standardIOHandlerWithInput = new StandardIOHandler(input, new PrintStream(output));
        assertEquals("Hello", standardIOHandlerWithInput.readFromStdIn());
    }

    @Test
    public void printsToConsole() {
        standardIOHandler.printToStdOut("hhehehhe");
        assertTrue(output.toString().contains("hhehehhe"));
    }
}
