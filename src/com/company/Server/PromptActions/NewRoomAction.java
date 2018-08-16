package com.company.Server.PromptActions;

import com.company.Server.ClientThread;

import java.io.IOException;

public class NewRoomAction extends PromptAction {

    @Override
    public void run(ClientThread clientThread, String userInput) throws IOException, IllegalAccessException {
        try {
            clientThread.getChatServer().createNewRoom(userInput);
            clientThread.getSocketIOHandler().printToSocket("New Room has been creates");
        } catch (IllegalAccessException e) {
            clientThread.getSocketIOHandler().printToSocket("Failure in creating a new room");
        }
    }
}
