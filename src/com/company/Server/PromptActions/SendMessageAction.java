package com.company.Server.PromptActions;

import com.company.Server.ClientThread;

public class SendMessageAction extends PromptAction {

    @Override
    public void run(ClientThread clientThread, String userInput) {
        try {
            clientThread.getChatServer().sendMessage(userInput, clientThread.getClientName());
            clientThread.getSocketIOHandler().printToSocket("Message has been sent.");
        } catch (IllegalAccessException e) {
            clientThread.getSocketIOHandler().printToSocket("Message not sent - invalid syntax.");
        }
    }
}

