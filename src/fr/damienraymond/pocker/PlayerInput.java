package fr.damienraymond.pocker;

import fr.damienraymond.pocker.utils.UserInput;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by damien on 11/11/2015.
 */
public class PlayerInput extends UserInput {
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
        Map<String, Integer> choices = PlayerInput.getChoices(bigBlindAmount, amountToCall, playerCanCheck);
        return choices.entrySet().stream()
                .map(e -> e.getKey() + " : " + Integer.toString(e.getValue()))
                .collect(Collectors.joining(", "));
    }
}
