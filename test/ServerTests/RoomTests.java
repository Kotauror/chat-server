package ServerTests;

import Mocks.MockChatServer;
import Mocks.MockClientThread;
import Mocks.MockServerSocket;
import Mocks.MockSocket;
import com.company.Server.ClientThread;
import com.company.Server.CurrentThreadExecutor;
import com.company.Server.Parser;
import com.company.Server.Room;
import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTests {

    private Room room;
    private MockClientThread mockClientThread;

    @Before
    public void setup() throws IOException {
        // MockSocket
        ByteArrayOutputStream mockOutputStreamClientOne = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStreamClientOne = new ByteArrayInputStream("$MESSAGE & Thread-13 & Hello".getBytes());
        MockSocket mockSocketOne = new MockSocket(mockOutputStreamClientOne, mockInputStreamClientOne);

        // Server
        MockServerSocket mockServerSocket = new MockServerSocket(mockOutputStreamClientOne, mockSocketOne);
        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("test String".getBytes());
        ByteArrayOutputStream mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);
        Executor executor = new CurrentThreadExecutor();
        Parser parser = new Parser();
        Boolean[] shouldRunServerBooleans = {true, false};

        MockChatServer mockServer = new MockChatServer(mockServerSocket, standardIOHandler, executor, parser, shouldRunServerBooleans);

        // ClientThread
        mockClientThread = new MockClientThread(mockSocketOne, mockServer, parser);

        room = new Room("test room");
    }

    @Test
    public void getRoomName() {
        assertEquals("test room", room.getRoomName());
    }

    @Test
    public void usersOfRoomInitiallyEmpty() {
        assertEquals(0, room.getUsersOfRoom().size());
    }

    @Test
    public void addClientToRoom() {
        room.addClientToRoom(mockClientThread);

        assertEquals(mockClientThread, room.getUsersOfRoom().get(0));
    }
}
