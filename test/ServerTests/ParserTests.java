package ServerTests;

import com.company.Server.Parser;
import org.junit.Assert;
import org.junit.Test;

public class ParserTests {

    @Test
    public void parseMessage() {
        Parser parser = new Parser();
        String[] words = new String[3];
        words[0] = "$MESSAGE";
        words[1] = "kot";
        words[2] = "hehehhe lololol";
        Assert.assertArrayEquals(words, parser.parseMessage("$MESSAGE & kot & hehehhe lololol"));
    }
}
