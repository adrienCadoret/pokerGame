package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.Level;
import fr.damienraymond.poker.utils.HandTypeComparator;
import fr.damienraymond.poker.utils.HandUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 02/10/2015.
 */
public class Hand implements Comparable<Hand> {

    protected Map<Integer, Card> cards;

    public Hand(Map<Integer, Card> cards){
        if (cards.size() > 5)
            throw new IllegalArgumentException("Hand is only 5 card, no more");
        this.cards = cards;
    }

    public Hand() {
        this(new HashMap<>(5));
    }

    public Hand addCard(Card c) {
        int indexNextElement = this.getCardNumber();
        cards.put(indexNextElement, c);
        // TODO : add factory ?
        return new Hand(this.cards);
    }


    public Card get(int i) {
        Card res = cards.get(i);
        if (res == null)
            throw new IllegalArgumentException("Cannot get this card, please check card number before");
        return res;
    }

    public int getCardNumber() {
        return cards.size();
    }

    public List<Card> getCards() {
        return new LinkedList<>(cards.values());
    }

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
    public int compareTo(Hand c) {
        HandType handTypeOfThis   = this.getHandType();
        HandType handTypeOfOParam = c.getHandType();

        int res;
        // If both hand are basics hand, let's take the best card
        if(handTypeOfOParam == HandType.BASIC_HAND && handTypeOfThis == HandType.BASIC_HAND){
            Card highestCardOfThis  = HandUtils.getHightestCard(this.getCards());
            Card highestCardOfParam = HandUtils.getHightestCard(this.getCards());
            res = highestCardOfThis.compareTo(highestCardOfParam);
        }else{
            HandTypeComparator handTypeComparator = new HandTypeComparator();
            res = handTypeComparator.compare(handTypeOfThis, handTypeOfOParam);
        }
        return res;
    }

    public HandType getHandType(){
        HandType handType = HandType.BASIC_HAND;
        if(this.isPair()){
            handType = HandType.PAIR;
        }else if(this.isDoublePair()){
            handType = HandType.DOUBLE_PAIR;
        }else if(this.isThreeOfAKind()){
            handType = HandType.THREE_OF_A_KIND;
        }else if(this.isStraight()){
            handType = HandType.STRAIGHT;
        }else if(this.isFlush()){
            handType = HandType.FLUSH;
        }else if(this.isFullHouse()){
            handType = HandType.FULL_HOUSE;
        }else if(this.isFourOfAKind()){
            handType = HandType.FOUR_OF_A_KIND;
        }else if(this.isStraightFlush()){
            handType = HandType.STRAIGHT;
        }else if(this.isRoyalFlush()){
            handType = HandType.ROYAL_FLUSH;
        }
        return handType;
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
