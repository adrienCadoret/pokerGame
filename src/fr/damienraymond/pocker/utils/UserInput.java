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


    private static Integer readInt(String question){
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        return scanner.nextInt();
    }

    private static boolean isValidUserInput(List<Integer> correctValues, Integer userInput) {
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

}
