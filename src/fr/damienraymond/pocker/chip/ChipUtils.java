package fr.damienraymond.pocker.chip;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by damien on 11/11/2015.
 */
public class ChipUtils {
    public static Set<Chip> getInitChipStackForPlayer(final int amount){
        List<Integer> values = Chip.getAvailableValues();
        values = values
                .stream()
                .filter(e -> e <= amount)
                .collect(Collectors.toList());

        // Then sort and reverse the list
        Collections.sort(values);
        Collections.reverse(values);

        Set<Chip> chips = new HashSet<>();

        int numberOfChipForValue;
        int amountLoop = amount;
        for (Integer value : values) {
            numberOfChipForValue = amountLoop % value;
            IntStream.range(0, numberOfChipForValue)
                    .forEach(e -> chips.add(Chip.valueOf(value)));
            amountLoop = amountLoop % value;
        }
        return chips;
    }
}
