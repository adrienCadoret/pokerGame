package fr.damienraymond.pocker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by damien on 02/10/2015.
 */
public class HandConcrete implements Hand {

    private Map<Integer, Card> cards;

    public HandConcrete(Map<Integer, Card> cards){
        if (cards.size() > 5)
            throw new IllegalArgumentException("Hand is only 5 card, no more");
        this.cards = cards;
    }

    private HandConcrete() {
        this(new HashMap<>(5));
    }


    @Override
    public Hand addCard(Card c) {
        // The index is one less than the number of elements
        int index = this.getCardNumber() - 1;
        cards.put(index, c);
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
}
