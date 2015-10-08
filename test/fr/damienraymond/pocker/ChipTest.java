package fr.damienraymond.pocker;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
    public void testGetAvailablesValue() throws Exception {
        assertTrue(Arrays.asList(25, 50, 100, 500, 1_000, 5_000, 10_000).containsAll(Chip.getAvailablesValue()));
    }
}