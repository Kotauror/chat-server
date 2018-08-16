package com.company.Server.PromptActions;

import com.company.Server.ClientThread;

public class SetNameAction extends PromptAction {

    @Override
    public void run(ClientThread clientThread, String userInput) {
        String clientName = userInput.substring(6, userInput.length()).trim();
        clientThread.setClientName(clientName);
        clientThread.getSocketIOHandler().printToSocket("Your name was set to be " + clientName);
    }
}
