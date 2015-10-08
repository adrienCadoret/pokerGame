package fr.damienraymond.pocker;

import org.junit.Test;

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
}