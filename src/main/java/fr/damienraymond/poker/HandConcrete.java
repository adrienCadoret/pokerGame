package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by damien on 02/10/2015.
 */
public class HandConcrete implements Hand {

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
}
