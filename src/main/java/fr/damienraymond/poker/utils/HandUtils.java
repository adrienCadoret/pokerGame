package fr.damienraymond.poker.utils;

import fr.damienraymond.poker.Hand;
import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.Color;
import fr.damienraymond.poker.card.Level;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 15/11/2015.
 */
public class HandUtils {

    public static Level getHighestLevel(List<Card> cards){
        return Collections.max(cards).getLevel();
    }

    public static Map<Level, List<Card>> groupBySameCardLevel(List<Card> cards){
        return cards.stream().collect(Collectors.groupingBy(Card::getLevel));
    }

    public static Map<Color, List<Card>> groupBySameCardColor(List<Card> cards){
        return cards.stream().collect(Collectors.groupingBy(Card::getColor));
    }

    public static boolean sameColor(List<Card> cards){
        return HandUtils.groupBySameCardColor(cards).size() == 1;
    }

    public static boolean isSequence(List<Card> cards){
        List<Integer> sortedLevels = cards.stream().map(c -> c.getLevel().getValue()).sorted().collect(Collectors.toList());
        return (sortedLevels.get(4) - sortedLevels.get(0)) == 4;
    }

    public static long getNumberOfSameLevelCard(List<Card> cards, int number){
        return HandUtils.groupBySameCardLevel(cards)
                .entrySet().stream().filter(e -> e.getValue().size() == number).count();
    }

    public static Hand findBestHand(NonEmptySet<Hand> hands){
        return hands.stream().reduce(hands.getOneElement(), (a, b) -> {
            if(a.compareTo(b) >= 0){
                return a;
            }else{
                return b;
            }
        });
    }

}
