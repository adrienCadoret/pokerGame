package fr.damienraymond.poker.utils;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.Color;
import fr.damienraymond.poker.card.Level;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by damien on 15/11/2015.
 */
public class HandUtilsTest {

    @Test
    public void testGetHighestCard1() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.KING, Color.CLUB);
        Card c4 = new Card(Level.QUEEN, Color.CLUB);
        Card c5 = new Card(Level.TEN, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);
        assertEquals(Level.KING, HandUtils.getHighestLevel(cards));
    }

    @Test
    public void testGetHighestCard2() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.KING, Color.CLUB);
        Card c4 = new Card(Level.KING, Color.CLUB);
        Card c5 = new Card(Level.TEN, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);
        assertEquals(Level.KING, HandUtils.getHighestLevel(cards));
    }

    @Test
    public void testGroupBySameCardLevel() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.KING, Color.CLUB);
        Card c4 = new Card(Level.KING, Color.CLUB);
        Card c5 = new Card(Level.TEN, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);

        Map<Level, List<Card>> expectedRes = new HashMap<>();
        expectedRes.put(Level.SEVEN, Arrays.asList(c1));
        expectedRes.put(Level.HEIGHT, Arrays.asList(c2));
        expectedRes.put(Level.KING, Arrays.asList(c3, c4));
        expectedRes.put(Level.TEN, Arrays.asList(c5));

        assertEquals(expectedRes, HandUtils.groupBySameCardLevel(cards));
    }

    @Test
    public void testGroupBySameCardColor() throws Exception {

        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.DIAMOND);
        Card c3 = new Card(Level.KING, Color.HEART);
        Card c4 = new Card(Level.KING, Color.CLUB);
        Card c5 = new Card(Level.TEN, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);

        Map<Color, List<Card>> expectedRes = new HashMap<>();
        expectedRes.put(Color.CLUB, Arrays.asList(c1, c4, c5));
        expectedRes.put(Color.DIAMOND, Arrays.asList(c2));
        expectedRes.put(Color.HEART, Arrays.asList(c3));

        assertEquals(expectedRes, HandUtils.groupBySameCardColor(cards));
    }

    @Test
    public void testSameColor1() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.KING, Color.CLUB);
        Card c4 = new Card(Level.QUEEN, Color.CLUB);
        Card c5 = new Card(Level.TEN, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);
        assertTrue(HandUtils.sameColor(cards));
    }


    @Test
    public void testSameColor2() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.KING, Color.DIAMOND);
        Card c4 = new Card(Level.QUEEN, Color.CLUB);
        Card c5 = new Card(Level.TEN, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);
        assertFalse(HandUtils.sameColor(cards));
    }

    @Test
    public void testIsSequence1() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.NINE, Color.DIAMOND);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.JACK, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);
        assertTrue(HandUtils.isSequence(cards));
    }

    @Test
    public void testIsSequence2() throws Exception {
        Card c1 = new Card(Level.SEVEN, Color.CLUB);
        Card c2 = new Card(Level.HEIGHT, Color.CLUB);
        Card c3 = new Card(Level.NINE, Color.DIAMOND);
        Card c4 = new Card(Level.TEN, Color.CLUB);
        Card c5 = new Card(Level.KING, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);
        assertFalse(HandUtils.isSequence(cards));
    }

    @Test
    public void testGetNumberOfSameLevelCard() throws Exception {
        Card c1 = new Card(Level.TEN, Color.CLUB);
        Card c2 = new Card(Level.TEN, Color.DIAMOND);
        Card c3 = new Card(Level.KING, Color.HEART);
        Card c4 = new Card(Level.KING, Color.CLUB);
        Card c5 = new Card(Level.TEN, Color.CLUB);

        List<Card> cards = Arrays.asList(c1, c2, c3, c4, c5);
        assertEquals(1, HandUtils.getNumberOfSameLevelCard(cards, 2));
        assertEquals(1, HandUtils.getNumberOfSameLevelCard(cards, 3));
        assertEquals(0, HandUtils.getNumberOfSameLevelCard(cards, 1));
    }
}