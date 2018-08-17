package com.company.Server.PromptActions;

import com.company.Server.ClientThread;

import java.io.IOException;

public class ShowRoomsAction extends PromptAction {

    @Override
    public void run(ClientThread clientThread, String userInput) throws IOException, IllegalAccessException {
        String roomsNames = clientThread.getChatServer().getRoomsNames();
        clientThread.getSocketIOHandler().printToSocket("There are following rooms: " + roomsNames);}
}
