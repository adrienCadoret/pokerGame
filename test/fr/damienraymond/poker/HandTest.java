package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.Color;
import fr.damienraymond.poker.card.Level;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by damien on 08/10/2015.
 */
public class HandTest {

    private Hand hand;


    @Before
    public void setUp() throws Exception {
        hand = new Hand();
    }


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

    @Test(expected = IndexOutOfBoundsException.class)
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
        assertEquals(hand.getCardNumber(), hand.cards.size());
    }

    @Test
    public void testGetCards() throws Exception {

    }

    @Test
    public void testEmpty() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testCompareTo() throws Exception {

    }

    @Test
    public void testGetHandType() throws Exception {

    }



    @Test
    public void testIsPair1() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.JACK, Color.DIAMOND);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isPair());
    }


    @Test
    public void testIsPair2() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.TEN, Color.CLUB);
        Card c3 = new Card(Level.JACK, Color.DIAMOND);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertFalse(hand.isPair());
    }

    @Test
    public void testIsDoublePair1() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.TEN, Color.CLUB);
        Card c3 = new Card(Level.JACK, Color.DIAMOND);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isDoublePair());
    }

    @Test
    public void testIsDoublePair2() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.JACK, Color.DIAMOND);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertFalse(hand.isDoublePair());
    }

    @Test
    public void testIsThreeOfAKind() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.JACK, Color.CLUB);
        Card c3 = new Card(Level.JACK, Color.DIAMOND);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isThreeOfAKind());
    }

    @Test
    public void testIsStraight1() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.NINE, Color.DIAMOND);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isStraight());
    }

    @Test
    public void testIsStraight2() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.NINE, Color.CLUB);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertFalse(hand.isStraight());
    }

    @Test
    public void testIsFlush1() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.NINE, Color.CLUB);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertFalse(hand.isFlush());
    }

    @Test
    public void testIsFlush2() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.FOUR, Color.CLUB);
        Card c3 = new Card(Level.NINE, Color.CLUB);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isFlush());
    }

    @Test
    public void testIsFullHouse() throws Exception {
        Card c1 = new Card(Level.KING, Color.CLUB);
        Card c2 = new Card(Level.KING, Color.DIAMOND);
        Card c3 = new Card(Level.KING, Color.HEART);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.TEN, Color.DIAMOND);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isFullHouse());
    }

    @Test
    public void testIsFourOfAKind() throws Exception {
        Card c1 = new Card(Level.KING, Color.CLUB);
        Card c2 = new Card(Level.KING, Color.DIAMOND);
        Card c3 = new Card(Level.KING, Color.HEART);
        Card c4 = new Card(Level.KING, Color.SPADE);
        Card c5 = new Card(Level.TEN, Color.DIAMOND);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isFourOfAKind());
    }

    @Test
    public void testIsStraightFlush1() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.NINE, Color.CLUB);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isStraightFlush());
    }

    @Test
    public void testIsStraightFlush2() throws Exception {
        Card c1 = new Card(Level.TEN, Color.CLUB);
        Card c2 = new Card(Level.JACK, Color.CLUB);
        Card c3 = new Card(Level.QUEEN, Color.CLUB);
        Card c4 = new Card(Level.KING, Color.CLUB);
        Card c5 = new Card(Level.AS, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertFalse(hand.isStraightFlush());
    }

    @Test
    public void testIsRoyalFlush() throws Exception {
        Card c1 = new Card(Level.TEN, Color.CLUB);
        Card c2 = new Card(Level.JACK, Color.CLUB);
        Card c3 = new Card(Level.QUEEN, Color.CLUB);
        Card c4 = new Card(Level.KING, Color.CLUB);
        Card c5 = new Card(Level.AS, Color.CLUB);

        hand.cards = Arrays.asList(c1, c2, c3, c4, c5);

        assertTrue(hand.isRoyalFlush());
    }
}