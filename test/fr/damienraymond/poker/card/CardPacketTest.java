package fr.damienraymond.poker.card;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by damien on 04/10/2015.
 */
public class CardPacketTest {

    @org.junit.Test
    public void testGenerateCardPacket() throws Exception {

    }

    @org.junit.Test
    public void testCartesianProductToProduceCardPacket() throws Exception {
        List<Level> levels = new ArrayList<>(EnumSet.allOf(Level.class));
        List<Color> colors = new ArrayList<>(EnumSet.allOf(Color.class));
        List<Card> cards = new CardPacket().cartesianProductToProduceCardPacket(levels, colors);
        int cardNumber = levels.size() * colors.size();
        assertEquals(cards.size(), cardNumber);

    }

    @org.junit.Test
    public void testPermute() throws Exception {
        List<Level> levels = new ArrayList<>(EnumSet.allOf(Level.class));
        List<Color> colors = new ArrayList<>(EnumSet.allOf(Color.class));
        List<Card> cards = new CardPacket().cartesianProductToProduceCardPacket(levels, colors);
        List<Card> cardsPermuted = new CardPacket().permute(cards);
        assertNotEquals(cards.toString(), cardsPermuted.toString());
        assertNotEquals(cardsPermuted.toString(), new CardPacket().permute(cardsPermuted).toString());
    }

    @org.junit.Test(expected = CardPacketException.class)
    public void testPopCard() throws CardPacketException {
        List<Level> levels = new ArrayList<>(EnumSet.allOf(Level.class));
        List<Color> colors = new ArrayList<>(EnumSet.allOf(Color.class));
        List<Card> cards = new CardPacket().cartesianProductToProduceCardPacket(levels, colors);
        CardPacket cp = new CardPacket();
        cp.cards = cards.iterator();


        int cardNumber = levels.size() * colors.size();
        for (int i = 0; i < cardNumber; i++) {
            cp.popCard();
        }
        cp.popCard();
    }
}