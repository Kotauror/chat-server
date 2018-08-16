package ServerTests;

import com.company.Server.Room;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTests {

    private Room room;

    @Before
    public void setup() {
        room = new Room("test room");
    }

    @Test
    public void getRoomName() {
        assertEquals("test room", room.getRoomName());
    }
}
