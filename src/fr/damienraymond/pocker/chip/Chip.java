package fr.damienraymond.pocker.chip;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 02/10/2015.
 */
public enum Chip {
    GREEN_25    (25),
    BLUE_50     (50),
    BLACK_100   (100),
    PURPLE_500  (500),
    YELLOW_1000 (1_000),
    PINK_5000   (5_000),
    BROWN_10000 (10_000);

    private int value;

    Chip(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    /**
     * Get all chips values (int)
     * @return a set of int thats corresponds to Chips values
     */
    public static List<Integer> getAvailableValues(){
        return Arrays.asList(Chip.values())
                .stream()                                           // Change list chip list to a stream
                .map(Chip::getValue)                                // get each chip value
                .collect(Collectors.toCollection(ArrayList::new));  // From this, get a set of values
    }


    public static Chip valueOf(Integer i){
        return Arrays.asList(Chip.values())
                .stream()
                .filter(e -> i.equals(e.getValue()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(i + " is not value of Chip"));
    }
}
