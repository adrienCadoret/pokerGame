package fr.damienraymond.poker.chip;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by damien on 11/11/2015.
 */
public class ChipUtils {


    public static List<Chip> getChipsListFromAmount(final int amount){
        List<Integer> values = Chip.getAvailableValues();
        values = values
                .stream()
                .filter(e -> e <= amount)
                .collect(Collectors.toList());

        // Then sort and reverse the list
        Collections.sort(values);
        Collections.reverse(values);

        List<Chip> chips = new LinkedList<>();

        int numberOfChipForValue;
        int amountLoop = amount;
        for (Integer value : values) {
            numberOfChipForValue = amountLoop / value;
            IntStream.range(0, numberOfChipForValue)
                    .forEach(e -> chips.add(Chip.valueOf(value)));
            amountLoop = amountLoop % value;
        }
        return chips;
    }


    public static List<Chip> getChipsListFromAmount(int smallBlindAmont, int amount){
        int chipNumber = amount / smallBlindAmont;
        return IntStream.range(0, chipNumber)
                .mapToObj(e -> Chip.valueOf(smallBlindAmont))
                .collect(Collectors.toList());
    }
}
