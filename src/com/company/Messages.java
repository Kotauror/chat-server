package com.company;

public abstract class Messages {

    public static String getRules() {
        return "To set your username, type $NAME: and your username after colon\n" +
                "To see the list od users, type $USERS\n" +
                "To send a message type $MESSAGE & UserNameOfAddressee & Here goes your message\n";
    }

    public static String informOfConnectionToServer() {
        return "Connected to a server";
    }

    public static String informOfNewClient() {
        return "A new socket has been connected";
    }

    public static String portNumberInfo(int portNumber) {
        return "Listening on port " + portNumber;
    }
}
