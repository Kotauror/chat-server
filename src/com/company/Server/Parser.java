package com.company.Server;

import java.util.ArrayList;

public class Parser {

    public Parser(){}

    public String[] parseMessage(String userInput) {
        String[] elementsOfInput = userInput.split("-");
        ArrayList<String> trimmedElements= new ArrayList<>();
        for (String element : elementsOfInput) {
            trimmedElements.add(element.trim());
        }
        return trimmedElements.toArray(new String[trimmedElements.size()]);
    }
}
