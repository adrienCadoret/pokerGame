package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.Color;
import fr.damienraymond.poker.card.Level;
import fr.damienraymond.poker.utils.HandUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 02/10/2015.
 */
public class HandConcrete implements Hand, Comparable<Hand> {

    protected Map<Integer, Card> cards;

    public HandConcrete(Map<Integer, Card> cards){
        if (cards.size() > 5)
            throw new IllegalArgumentException("Hand is only 5 card, no more");
        this.cards = cards;
    }

    public HandConcrete() {
        this(new HashMap<>(5));
    }


    @Override
    public Hand addCard(Card c) {
        int indexNextElement = this.getCardNumber();
        cards.put(indexNextElement, c);
        // TODO : add factory ?
        return new HandConcrete(this.cards);
    }


    @Override
    public Card get(int i) {
        Card res = cards.get(i);
        if (res == null)
            throw new IllegalArgumentException("Cannot get this card, please check card number before");
        return res;
    }

    @Override
    public int getCardNumber() {
        return cards.size();
    }

    @Override
    public List<Card> getCards() {
        return new LinkedList<>(cards.values());
    }

    @Override
    public void empty() {
        cards = new HashMap<>(5); // TODO change to 2
    }

    @Override
    public String toString() {
        return cards.values().stream()
                .map(Card::toString)
                .collect(Collectors.joining(", "));
    }

    @Override
    public int compareTo(Hand o) {
        return 0;
    }


    public Card getHightestCard(Card c){
        return Collections.max(getCards());
    }

    public boolean isPair(){
        Map<Level, List<Card>> levelListMap = HandUtils.groupBySameCardLevel(getCards());
        return levelListMap.size() == 4;
    }

    public boolean isDoublePair(){
        return HandUtils.getNumberOfSameLevelCard(getCards(), 2) == 2;
    }

    public boolean isThreeOfAKind(){
        boolean isThereTreeIdenticalCards = HandUtils.getNumberOfSameLevelCard(getCards(), 3) == 1;
        boolean isThereNoPair             = HandUtils.getNumberOfSameLevelCard(getCards(), 2) == 0;
        return isThereTreeIdenticalCards && isThereNoPair;
    }

    public boolean isStraight(){
        return HandUtils.isSequence(getCards()) && (! HandUtils.sameColor(getCards()));
    }

    public boolean isFlush(){
        return HandUtils.sameColor(getCards());
    }

    public boolean isFullHouse(){
        boolean isThereTreeIdenticalCards = HandUtils.getNumberOfSameLevelCard(getCards(), 3) == 1;
        boolean isTherePair               = HandUtils.getNumberOfSameLevelCard(getCards(), 2) == 1;
        return isThereTreeIdenticalCards && isTherePair;
    }

    public boolean isFourOfAKind(){
        return HandUtils.getNumberOfSameLevelCard(getCards(), 4) == 1;
    }

    public boolean isStraightFlush(){
        return HandUtils.isSequence(getCards());
    }

    public boolean isRoyalFlush(){
        List<Card> cards = getCards();
        boolean containsAsLevel = cards.stream().anyMatch(c -> c.getLevel().equals(Level.AS));
        return containsAsLevel && HandUtils.isSequence(cards);
    }
}
