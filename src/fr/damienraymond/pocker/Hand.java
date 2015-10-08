package fr.damienraymond.pocker;

/**
 * Created by damien on 08/10/2015.
 */
public interface Hand {

    Hand addCard(Card c);
    Card get(int i);
    int getCardNumber();

}
