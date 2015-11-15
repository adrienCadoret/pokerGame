package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.Color;
import fr.damienraymond.poker.card.Level;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by damien on 08/10/2015.
 */
public class HandTest {

    @Test
    public void testAddCard() throws Exception {
        Hand hand = new Hand();
        hand = hand.addCard(new Card(Level.AS, Color.CLUB));
        assertEquals(((Hand) hand).cards.size(), 1);
        hand = hand.addCard(new Card(Level.FIVE, Color.HEART));
        assertEquals(((Hand) hand).cards.size(), 2);
        hand = hand.addCard(new Card(Level.AS, Color.DIAMOND));
        assertEquals(((Hand) hand).cards.size(), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCardException() throws Exception {
        Hand hand = new Hand();
        hand.addCard(new Card(Level.AS, Color.CLUB))
            .addCard(new Card(Level.FIVE, Color.HEART))
            .addCard(new Card(Level.AS, Color.DIAMOND))
            .addCard(new Card(Level.AS, Color.DIAMOND))
            .addCard(new Card(Level.AS, Color.DIAMOND))
            .addCard(new Card(Level.AS, Color.DIAMOND));
    }

    @Test
    public void testGet() throws Exception {
        Hand hand = new Hand()
                .addCard(new Card(Level.AS, Color.CLUB));
        assertEquals(hand.getCardNumber(), 1);
        assertEquals(hand.get(0), new Card(Level.AS, Color.CLUB));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetException() throws Exception {
        Hand hand = new Hand();
        hand.get(0);
    }

    @Test
    public void testGetCardNumber() throws Exception {
        Hand hand = new Hand();
        hand = hand.addCard(new Card(Level.AS, Color.CLUB))
                .addCard(new Card(Level.FIVE, Color.HEART))
                .addCard(new Card(Level.AS, Color.DIAMOND))
                .addCard(new Card(Level.AS, Color.DIAMOND))
                .addCard(new Card(Level.AS, Color.DIAMOND));
        assertEquals(hand.getCardNumber(), ((Hand) hand).cards.size());
    }
}