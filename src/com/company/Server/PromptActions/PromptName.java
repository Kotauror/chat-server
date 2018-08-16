package com.company.Server.PromptActions;

public enum PromptName {

    SET_NAME("$NAME"),
    USERS("$USERS"),
    MESSAGE("$MESSAGE");

    private final String value;

    PromptName(String promptNameString) {
        this.value = promptNameString;
    }

    public String value() {
        return value;
    }
}
