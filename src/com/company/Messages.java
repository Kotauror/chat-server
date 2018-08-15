package com.company;

public abstract class Messages {

    public static String getPrompts() {
        return "To set your username, type $NAME: and your username after colon\n" +
                "To see the list od users, type $USERS\n" +
                "To send a message type $MESSAGE & UserNameOfAddressee & Here goes your message";
    }

    public static String informOfConnectionToServer() {
        return "Connected to a server";
    }

    public static String newClientInServer() {
        return "A new socket has been connected";
    }

    public static String informOfException(int portNumber) {
        return "Exception caught when trying to listen on port: " + portNumber;
    }

    public static String printServerPort(int portNumber) {
        return "Listening on port " + portNumber;
    }


}
