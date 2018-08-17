package ServerTests;

import Mocks.MockChatServer;
import Mocks.MockServerSocketTwoClients;
import Mocks.MockSocket;
import com.company.Server.ClientThread;
import com.company.Server.CurrentThreadExecutor;
import com.company.Server.Parser;
import com.company.Server.Room;
import com.company.StandardIOHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatServerTest {

    private MockChatServer mockServer;
    private ByteArrayOutputStream mockUserOutput;
    private ByteArrayOutputStream mockOutputStreamClientOne;
    private ByteArrayOutputStream mockOutputStreamClientTwo;

    @Before
    public void setup() throws IOException {
        // Client1
        mockOutputStreamClientOne = new ByteArrayOutputStream();
        MockSocket mockSocketOne = createMockSocketWithInput("$MESSAGE & Thread-19 & Hello", mockOutputStreamClientOne);

        // Client2
        mockOutputStreamClientTwo = new ByteArrayOutputStream();
        MockSocket mockSocketTwo = createMockSocketWithInput("", mockOutputStreamClientTwo);

        // Server Socket
        ByteArrayOutputStream mockOutputStream = new ByteArrayOutputStream();
        MockServerSocketTwoClients mockServerSocketTwoClients = new MockServerSocketTwoClients(mockOutputStream, mockSocketOne, mockSocketTwo);

        // Server StandardIOHandler
        StandardIOHandler standardIOHandler = createServerStandardIOHandler();

        Executor executor = new CurrentThreadExecutor();
        Parser parser = new Parser();
        Boolean[] shouldRunServerBooleans = {true, true, false};
        mockServer = new MockChatServer(mockServerSocketTwoClients, standardIOHandler, executor, parser, shouldRunServerBooleans);
    }

    @Test
    public void printsInfoAboutListeningOnPortAndNewConnection() {
        mockServer.run();

        assertEquals("Listening on port -1\nA new socket has been connected\nA new socket has been connected", mockUserOutput.toString().trim());
    }

    @Test(expected= IllegalAccessException.class)
    public void sendMessageThrowsErrorOnInvalidMessage() throws IllegalAccessException {
        mockServer.sendMessage("admin", "test invalid string");
    }

    @Test
    public void messageIsSend() throws IllegalAccessException, IOException {
        mockServer.run();

        mockServer.sendMessage("$MESSAGE & Thread-20 & Hello", "Thread-19");

        // Client one perspective
        assertEquals("Connected to a server\n" +
                "---------- INSTRUCTIONS ----------\n" +
                "> To set your username, type $NAME username\n" +
                "> To see the list od users, type $USERS\n" +
                "> To send a message type $MESSAGE & UserNameOfAddressee & your message\n" +
                "> To create a new chat room type $NEW_ROOM roomName\n" +
                "> To see a list of rooms, type $ROOMS\n" +
                "----------------------------------\n\n" +
                "💬  Thread-19: Hello", mockOutputStreamClientTwo.toString().trim());

        // Client two perspective
        assertEquals("Connected to a server\n" +
                "---------- INSTRUCTIONS ----------\n" +
                "> To set your username, type $NAME username\n" +
                "> To see the list od users, type $USERS\n" +
                "> To send a message type $MESSAGE & UserNameOfAddressee & your message\n" +
                "> To create a new chat room type $NEW_ROOM roomName\n" +
                "> To see a list of rooms, type $ROOMS\n" +
                "----------------------------------\n\n" +
                "💬  Thread-19: Hello\n" +
                "Message has been sent.", mockOutputStreamClientOne.toString().trim());
    }

    @Test
    public void createNewRoom() throws IllegalAccessException {
        mockServer.createNewRoom("$NEWROOM kotek");
        Room room = (Room) mockServer.getRooms().get(0);
        String roomName = room.getRoomName();

        assertEquals("kotek", roomName);
    }

    @Test
    public void tellNamesOfRooms() throws IllegalAccessException {
        mockServer.createNewRoom("$NEWROOM kotek");
        mockServer.createNewRoom("$NEWROOM piesek");

        assertEquals("kotek piesek ", mockServer.getRoomsNames());
    }

    @Test
    public void addClientToRoom() throws IllegalAccessException, IOException {
        ClientThread clientThread = createClientThreadWithInput("$ROOMS");

        mockServer.createNewRoom("$NEWROOM piesek");
        mockServer.addClientToRoom(clientThread, "piesek");

        Room room = (Room) mockServer.getRooms().get(0);

        assertEquals(clientThread, room.getUsersOfRoom().get(0));
    }

    @Test(expected= IllegalAccessException.class)
    public void throwsErrorWhenThereIsNoRoomToJoin() throws IllegalAccessException, IOException {
        ClientThread clientThread = createClientThreadWithInput("$ROOMS");

        mockServer.createNewRoom("$NEWROOM piesek");
        mockServer.addClientToRoom(clientThread, "teeeest");
    }

    private MockSocket createMockSocketWithInput(String input, ByteArrayOutputStream byteArrayOutputStream) {
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
        return new MockSocket(byteArrayOutputStream, mockInputStream);
    }

    private ClientThread createClientThreadWithInput(String input) throws IOException {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        return new ClientThread(mockClientSocket, mockServer, parser);
    }

    private StandardIOHandler createServerStandardIOHandler() {
        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        return new StandardIOHandler(mockUserInput, mockSystemOut);
    }
}
