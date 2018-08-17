package ServerTests;

import Mocks.MockChatServer;
import Mocks.MockServerSocket;
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

    private ByteArrayOutputStream mockOutputStream;
    private MockChatServer mockServer;
    private ByteArrayOutputStream mockUserOutput;
    private MockServerSocket mockServerSocket;
    private Parser parser;

    @Before
    public void setup() throws IOException {
        mockOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream("test String".getBytes());
        MockSocket mockSocket = new MockSocket(mockOutputStream, mockInputStream);

        mockServerSocket = new MockServerSocket(mockOutputStream, mockSocket);

        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("test String".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        Executor executor = new CurrentThreadExecutor();
        Parser parser = new Parser();
        Boolean[] shouldRunServerBooleans = {true, false};
        mockServer = new MockChatServer(mockServerSocket, standardIOHandler, executor, parser, shouldRunServerBooleans);
    }

    @Test
    public void printsInfoAboutListeningOnPortAndNewConnection() {
        mockServer.run();

        assertEquals("Listening on port -1\nA new socket has been connected", mockUserOutput.toString().trim());
    }

    @Test(expected= IllegalAccessException.class)
    public void sendMessageThrowsErrorOnInvalidMessage() throws IllegalAccessException {
        mockServer.sendMessage("admin", "test invalid string");
    }

    @Test
    public void messageIsSend() throws IllegalAccessException, IOException {
        // Client 1
        ByteArrayOutputStream mockOutputStreamClientOne = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStreamClientOne = new ByteArrayInputStream("$MESSAGE & Thread-19 & Hello".getBytes());
        MockSocket mockSocketOne = new MockSocket(mockOutputStreamClientOne, mockInputStreamClientOne);

        // Client 2
        ByteArrayOutputStream mockOutputStreamClientTwo = new ByteArrayOutputStream();
        ByteArrayInputStream mockInputStreamClientTwo = new ByteArrayInputStream("".getBytes());
        MockSocket mockSocketTwo = new MockSocket(mockOutputStreamClientTwo, mockInputStreamClientTwo);

        // ServerSocket
        ByteArrayOutputStream mockServerOutput = new ByteArrayOutputStream();
        MockServerSocketTwoClients mockServerSocketTwoClients = new MockServerSocketTwoClients(mockServerOutput, mockSocketOne, mockSocketTwo);

        // Server StandardIOHandler
        ByteArrayInputStream mockUserInput = new ByteArrayInputStream("$MESSAGE & Thread-19 & Hello".getBytes());
        mockUserOutput = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockUserOutput);
        StandardIOHandler standardIOHandler = new StandardIOHandler(mockUserInput, mockSystemOut);

        // Various Server dependencies
        Executor executor = new CurrentThreadExecutor();
        parser = new Parser();
        Boolean[] shouldRunServerBooleans = {true, true, false};

        // Complete Server
        mockServer = new MockChatServer(mockServerSocketTwoClients, standardIOHandler, executor, parser, shouldRunServerBooleans);

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
        // ClientThread
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$ROOMS".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        ClientThread clientThread = new ClientThread(mockClientSocket, mockServer, parser);

        mockServer.createNewRoom("$NEWROOM piesek");
        mockServer.addClientToRoom(clientThread, "piesek");

        Room room = (Room) mockServer.getRooms().get(0);

        assertEquals(clientThread, room.getUsersOfRoom().get(0));
    }

    @Test(expected= IllegalAccessException.class)
    public void throwsErrorWhenThereIsNoRoomToJoin() throws IllegalAccessException, IOException {
        // ClientThread
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("$ROOMS".getBytes());
        MockSocket mockClientSocket = new MockSocket(outputStream, inputStream);
        ClientThread clientThread = new ClientThread(mockClientSocket, mockServer, parser);

        mockServer.createNewRoom("$NEWROOM piesek");
        mockServer.addClientToRoom(clientThread, "teeeest");
    }
}
