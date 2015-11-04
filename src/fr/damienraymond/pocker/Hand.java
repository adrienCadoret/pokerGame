package fr.damienraymond.pocker;

import fr.damienraymond.pocker.card.Card;

import java.util.List;

/**
 * Created by damien on 08/10/2015.
 */
public interface Hand {

    Hand addCard(Card c);
    Card get(int i);
    int getCardNumber();

    List<Card> getCards();

    void empty();
}
