package fr.damienraymond.poker;

import fr.damienraymond.poker.chip.Chip;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by damien on 08/10/2015.
 */
public class ChipTest {

    @Test
    public void testConstructor() throws Exception {
        Chip c = Chip.BROWN_10000;
        assertEquals(c.getValue(), 10_000);
    }

    @Test
    public void testGetAvailableValues() throws Exception {
        assertTrue(Arrays.asList(25, 50, 100, 500, 1_000, 5_000, 10_000).containsAll(Chip.getAvailableValues()));
    }

    @Test
    public void testValueOf1() throws Exception {
        assertEquals(Chip.BLACK_100, Chip.valueOf(100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOf2() throws Exception {
        Chip.valueOf(1234);
    }
}