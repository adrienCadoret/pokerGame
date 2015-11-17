package fr.damienraymond.poker.utils;

import java.util.List;
import java.util.Scanner;

/**
 * Created by damien on 09/11/2015.
 */
public class UserInput {


    private static Integer readInt(String question){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println(question);
            return scanner.nextInt();
        }catch (Exception e){
            return -2;
        }
    }

    private static boolean isValidUserInput(List<Integer> correctValues, int minValue, Integer maxValue, Integer userInput) {
        return correctValues.contains(userInput) || (
                        userInput >= minValue &&
                        userInput <= maxValue
                );
    }

    public static Integer readAndValidateUserInput(List<Integer> correctValues, int minValue, int maxValue, String question) {
        boolean goOn;
        Integer userInput;
        do{
            userInput = UserInput.readInt(question);
            goOn = isValidUserInput(correctValues, minValue, maxValue, userInput);
            if(! goOn){
                Logger.error("Please insert correct answer");
            }
        }while (! goOn);
//        }while (false);
        return userInput;
    }

}
