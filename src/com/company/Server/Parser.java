package com.company.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public Parser(){}

    static List<String> prompts = Arrays.asList("$NAME", "$USERS", "$MESSAGE", "$NEW_ROOM", "$ROOMS");

    public static String createMessage(String name, String message) {
        return "💬  " + name + ": " + message;
    }

    public String getPromptActionType(String userMessage) {
        String[] elementsOfInput = userMessage.split(" ");
         if (prompts.contains(elementsOfInput[0])) {
            return elementsOfInput[0];
        } else
            return "undefined";
    }

    public String[] parseMessage(String userInput, String userNames) {
        String[] elementsOfInput = userInput.split("&");
        ArrayList<String> trimmedElements= new ArrayList<>();
        for (String element : elementsOfInput) {
            trimmedElements.add(element.trim());
        }
        if (allElementsPresent(trimmedElements) && addresseeIsValid(trimmedElements, userNames)) {
            return trimmedElements.toArray(new String[trimmedElements.size()]);
        } else {
            return new String[]{"error"};
        }
    }

    public String getNameOfRoom(String userInput) {
        return userInput.substring(9, userInput.length()).trim();
    }

    private boolean allElementsPresent(ArrayList trimmedElements) {
        return trimmedElements.size() == 3;
    }

    private boolean addresseeIsValid(ArrayList trimmedElements, String userNames) {
        return userNames.contains((CharSequence) trimmedElements.get(1));
    }
}
