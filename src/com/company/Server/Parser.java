package com.company.Server;

import java.util.ArrayList;

public class Parser {

    public Parser(){}

    public static String createMessage(String name, String message) {
        return "💬  " + name + ": " + message;
    }

    public String getPromptActionType(String userMessage) {
        String[] elementsOfInput = userMessage.split(" ");
        if (elementsOfInput[0].equals("$NAME") || elementsOfInput[0].equals("$USERS") || elementsOfInput[0].equals("$MESSAGE")) {
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

    private boolean allElementsPresent(ArrayList trimmedElements) {
        return trimmedElements.size() == 3;
    }

    private boolean addresseeIsValid(ArrayList trimmedElements, String userNames) {
        return userNames.contains((CharSequence) trimmedElements.get(1));
    }
}
