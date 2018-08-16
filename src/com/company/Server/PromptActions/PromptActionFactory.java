package com.company.Server.PromptActions;

import java.util.HashMap;
import java.util.Map;

public class PromptActionFactory {

    private final HashMap<PromptName, PromptAction> promptActions;

    public PromptActionFactory() {
        this.promptActions = new HashMap();
        createPromptActions();
    }

    public PromptAction getPromptAction(String userInput) {
        for(Map.Entry<PromptName, PromptAction> promptAction : this.promptActions.entrySet()) {
            PromptName promptName = promptAction.getKey();
            if (promptName.value().equals(userInput)) {
                return promptAction.getValue();
            }
        }
        return new NotSpecifiedAction();
    }

    private void createPromptActions() {
        promptActions.put(PromptName.SET_NAME, new SetNameAction());
        promptActions.put(PromptName.USERS, new ShowUsersAction());
        promptActions.put(PromptName.MESSAGE, new SendMessageAction());
    }
}
