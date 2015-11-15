package fr.damienraymond.poker.card;

/**
 * Created by damien on 02/10/2015.
 */
public enum Level {
    TWO    (2),
    TREE   (3),
    FOUR   (4),
    FIVE   (5),
    SIX    (6),
    SEVEN  (7),
    HEIGHT (8),
    NINE   (9),
    TEN    (10),
    JACK   (11),
    QUEEN  (12),
    KING   (13),
    AS     (14);

    private int value;

    Level(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

}
