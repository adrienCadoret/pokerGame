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
                Logger.error("Please insert correct answer");
            }
        }while (! goOn);
//        }while (false);
        return userInput;
    }

}
