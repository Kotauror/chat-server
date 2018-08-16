package com.company.Server.PromptActions;

import com.company.Server.ClientThread;

import java.io.IOException;

public class NotSpecifiedAction extends PromptAction {

    @Override
    public void run(ClientThread clientThread, String userInput) throws IOException, IllegalAccessException {
        clientThread.handleUserInputCaller();
    }
}
