package fr.damienraymond.poker.card;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by damien on 15/11/2015.
 */
public class LevelTest {

    @Test
    public void testCompare1() throws Exception {
        assertTrue(Level.AS.compareTo(Level.FOUR) > 0);
    }

    @Test
    public void testCompare2() throws Exception {
        assertTrue(Level.FIVE.compareTo(Level.QUEEN) < 0);
    }

    @Test
    public void testCompare3() throws Exception {
        assertTrue(Level.QUEEN.compareTo(Level.QUEEN) == 0);
    }

}