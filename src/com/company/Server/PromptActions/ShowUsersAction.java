package com.company.Server.PromptActions;

import com.company.Server.ClientThread;

public class ShowUsersAction extends PromptAction {

    @Override
    public void run(ClientThread clientThread, String userInput) {
        String userNames = clientThread.getChatServer().getClientNames();
        clientThread.getSocketIOHandler().printToSocket("There are following users: " + userNames);
    }
}
