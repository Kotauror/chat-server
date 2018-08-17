package com.company;

public abstract class Messages {

    public static String getPrompts() {
        return "---------- INSTRUCTIONS ----------\n" +
                "> To set your username, type $NAME username\n" +
                "> To see the list od users, type $USERS\n" +
                "> To send a message type $MESSAGE & UserNameOfAddressee & your message\n" +
                "> To create a new chat room type $NEW_ROOM roomName\n" +
                "> To see a list of rooms, type $ROOMS\n" +
                "----------------------------------\n";
    }

    public static String informOfConnectionToServer() {
        return "Connected to a server";
    }

    public static String newClientInServer() {
        return "A new socket has been connected";
    }

    public static String printServerPort(int portNumber) {
        return "Listening on port " + portNumber;
    }
}
