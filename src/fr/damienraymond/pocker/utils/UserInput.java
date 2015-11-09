package fr.damienraymond.pocker.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by damien on 09/11/2015.
 */
public class UserInput {


    public static Integer readInt(String question){
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        return scanner.nextInt();
    }

    public static boolean isValidUserInput(List<Integer> correctValues, Integer userInput) {
        return correctValues.contains(userInput);
    }

    public static Integer readAndValidateUserInput(List<Integer> correctValues, String question) {
        boolean goOn = true;
        Integer userInput = null;
        do{
            userInput = UserInput.readInt(question);
            goOn = isValidUserInput(correctValues, userInput);
            if(! goOn){
                System.out.println("Please insert correct answer");
            }
        }while (goOn);
        return userInput;
    }

    public static Map<String, Integer> getChoices(int bigBlindAmount, int amountToCall, boolean playerCanCheck) {
        Map<String, Integer> choices = new HashMap<>();
        if(playerCanCheck){
            choices.put("Check", -1);
        }
        choices.put("Fold", 0);
        choices.put("Call", amountToCall);
        choices.put("Raise", amountToCall + bigBlindAmount);
        return choices;
    }

    public static String choicesToString(int bigBlindAmount, int amountToCall, boolean playerCanCheck) {
        Map<String, Integer> choices = UserInput.getChoices(bigBlindAmount, amountToCall, playerCanCheck);
        return choices.entrySet().stream()
                .map(e -> e.getKey() + " : " + Integer.toString(e.getValue()))
                .collect(Collectors.joining(", "));
    }
}
