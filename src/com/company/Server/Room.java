package com.company.Server;

import java.util.ArrayList;

public class Room {

    private final String name;
    private final ArrayList<ClientThread> usersOfRoom;

    public Room(String name) {
        this.name = name;
        this.usersOfRoom = new ArrayList<>();
    }

    public String getRoomName() {
        return this.name;
    }

    public ArrayList getUsersOfRoom() {
        return this.usersOfRoom;
    }

    public void addClientToRoom(ClientThread clientThread) {
        this.usersOfRoom.add(clientThread);
    }
}
