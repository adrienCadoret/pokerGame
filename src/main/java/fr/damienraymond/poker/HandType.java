package fr.damienraymond.poker;

/**
 * Created by damien on 15/11/2015.
 */
public enum HandType {
    BASIC_HAND      (15),
    PAIR            (16),
    DOUBLE_PAIR     (17),
    THREE_OF_A_KIND (18),
    STRAIGHT        (19),
    FLUSH           (20),
    FULL_HOUSE      (21),
    FOUR_OF_A_KIND  (22),
    STRAIGHT_FLUSH  (23),
    ROYAL_FLUSH     (24);

    private Integer value;

    HandType(Integer i) {
        this.value = i;
    }

    public Integer getValue() {
        return value;
    }

}
