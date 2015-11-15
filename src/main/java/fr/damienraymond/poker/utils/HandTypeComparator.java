package fr.damienraymond.poker.utils;

import fr.damienraymond.poker.HandType;

import java.util.Comparator;

/**
 * Created by damien on 15/11/2015.
 */
public class HandTypeComparator implements Comparator<HandType> {
    @Override
    public int compare(HandType o1, HandType o2) {
        return o1.getValue().compareTo(o2.getValue());
    }
}
