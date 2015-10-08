package fr.damienraymond.pocker;

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
}
