package ServerTests;

import com.company.Server.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTests {

    private Parser parser;

    @Before
    public void setup() {
        parser = new Parser();
    }

    @Test
    public void parseMessageOnValidInput() {
        String[] words = new String[3];
        words[0] = "$MESSAGE";
        words[1] = "kot";
        words[2] = "hehehhe lololol";
        Assert.assertArrayEquals(words, parser.parseMessage("$MESSAGE & kot & hehehhe lololol", "kot mis pies"));
    }

    @Test
    public void parseMessageOnInvalidInput_NoUser() {
        String[] words = new String[1];
        words[0] = "error";
        Assert.assertArrayEquals(words, parser.parseMessage("$MESSAGE & kot & hehehhe lololol", "mis pies"));
    }

    @Test
    public void parseMessageOnInvalidInput_WronglyUsedFirstAnd() {
        String[] words = new String[1];
        words[0] = "error";
        Assert.assertArrayEquals(words, parser.parseMessage("$MESSAGE  kot & hehehhe lololol", "mis pies"));
    }

    @Test
    public void parseMessageOnInvalidInput_WronglyUsedSecondAnd() {
        String[] words = new String[1];
        words[0] = "error";
        Assert.assertArrayEquals(words, parser.parseMessage("$MESSAGE & kot hehehhe lololol", "mis pies"));
    }

    @Test
    public void createMessage() {
        assertEquals("💬  user: message", parser.createMessage("user", "message"));
    }

    @Test
    public void getNameOfRoom() {
        assertEquals("kotek", parser.getNameOfRoom("$NEW_ROOM kotek"));
    }
}
