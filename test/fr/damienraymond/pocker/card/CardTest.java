package fr.damienraymond.pocker.card;

import fr.damienraymond.pocker.card.Card;
import fr.damienraymond.pocker.card.Color;
import fr.damienraymond.pocker.card.Level;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by damien on 23/10/2015.
 */
public class CardTest {

    @Test
    public void testGetColor() throws Exception {
        Card card = new Card(Level.AS, Color.CLUB);
        assertEquals(Color.CLUB, card.getColor());
    }

    @Test
    public void testGetLevel() throws Exception {
        Card card = new Card(Level.AS, Color.CLUB);
        assertEquals(Level.AS, card.getLevel());
    }

    @Test
    public void testEquals() throws Exception {
        Card card1 = new Card(Level.AS, Color.CLUB);
        Card card2 = new Card(Level.AS, Color.CLUB);
        Card card3 = new Card(Level.AS, Color.DIAMOND);

        assertEquals(card1, card2);
        assertNotEquals(card2, card3);
        assertNotEquals(card1, card3);
    }
    
}