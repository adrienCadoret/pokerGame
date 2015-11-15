package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.Level;
import fr.damienraymond.poker.utils.HandTypeComparator;
import fr.damienraymond.poker.utils.HandUtils;

import java.util.*;
import java.util.stream.Collectors;

import static fr.damienraymond.poker.utils.HandUtils.getNumberOfSameLevelCard;
import static fr.damienraymond.poker.utils.HandUtils.sameColor;

/**
 * Created by damien on 02/10/2015.
 */
public class Hand implements Comparable<Hand> {

    protected final int MAX_NUMBER_CARD = 5;

    protected List<Card> cards;

    public Hand(List<Card> cards){
        if (cards.size() > 5)
            throw new IllegalArgumentException("Hand is only 5 card, no more");
        this.cards = cards;
    }

    public Hand() {
        this(new ArrayList<>(5));
    }

    public Hand addCard(Card c) {
        if(this.getCardNumber() < MAX_NUMBER_CARD){
            cards.add(c);
        }else{
            throw new IllegalArgumentException("Cannot add another card because the size is limited");
        }
        // TODO : add factory ?
        return new Hand(this.cards);
    }


    public Card get(int i) {
        return cards.get(i);
    }

    public int getCardNumber() {
        return cards.size();
    }

    public List<Card> getCards() {
        return new LinkedList<>(cards);
    }

    public void empty() {
        cards = new ArrayList<>(5);
    }

    @Override
    public String toString() {
        return cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(", "));
    }

    @Override
    public int compareTo(Hand c) {
        HandType handTypeOfThis   = this.getHandType();
        HandType handTypeOfOParam = c.getHandType();

        int res = 0;
        // If both hand are basics hand, let's take the best card
        if(handTypeOfOParam == HandType.BASIC_HAND && handTypeOfThis == HandType.BASIC_HAND){
            Level highestLevelOfThis  = HandUtils.getHighestLevel(this.getCards());
            Level highestLevelOfParam = HandUtils.getHighestLevel(this.getCards());
            res = highestLevelOfThis.compareTo(highestLevelOfParam);
        }

        if(res == 0){
            // Otherwise
            //      - both hand are basic or
            //      - both a equals (try to get with the highest card)
            //          Some case are eclipsed; to be added later
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
        return getNumberOfSameLevelCard(getCards(), 2) == 2;
    }

    public boolean isThreeOfAKind(){
        boolean isThereTreeIdenticalCards = getNumberOfSameLevelCard(getCards(), 3) == 1;
        boolean isThereNoPair             = getNumberOfSameLevelCard(getCards(), 2) == 0;
        return isThereTreeIdenticalCards && isThereNoPair;
    }

    public boolean isStraight(){
        return HandUtils.isSequence(getCards()) && (! sameColor(getCards()));
    }

    public boolean isFlush(){
        return sameColor(getCards()) && ! HandUtils.isSequence(getCards());
    }

    public boolean isFullHouse(){
        boolean isThereTreeIdenticalCards = getNumberOfSameLevelCard(getCards(), 3) == 1;
        boolean isTherePair               = getNumberOfSameLevelCard(getCards(), 2) == 1;
        return isThereTreeIdenticalCards && isTherePair;
    }

    public boolean isFourOfAKind(){
        return getNumberOfSameLevelCard(getCards(), 4) == 1;
    }

    public boolean isStraightFlush(){
        boolean containsAsLevel = cards.stream().anyMatch(c -> c.getLevel().equals(Level.AS));
        return HandUtils.isSequence(getCards()) &&
                sameColor(getCards()) &&
                ! containsAsLevel;
    }

    public boolean isRoyalFlush(){
        List<Card> cards = getCards();
        boolean containsAsLevel = cards.stream().anyMatch(c -> c.getLevel().equals(Level.AS));
        return containsAsLevel &&
                HandUtils.isSequence(cards) &&
                sameColor(getCards());
    }
}
