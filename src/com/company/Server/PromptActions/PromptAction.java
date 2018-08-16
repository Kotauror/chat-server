package com.company.Server.PromptActions;

import com.company.Server.ClientThread;

import java.io.IOException;

public abstract class PromptAction {

    public abstract void run(ClientThread clientThread, String userInput) throws IOException, IllegalAccessException;
}
