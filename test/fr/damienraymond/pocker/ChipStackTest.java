package fr.damienraymond.pocker;

import fr.damienraymond.pocker.chip.Chip;
import fr.damienraymond.pocker.chip.ChipStack;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by damien on 11/10/2015.
 */
public class ChipStackTest {

    @Test
    public void testAddChip() throws Exception {

    }


    // TODO : what happen when the player cannot give the exact amount of money

    @Test
    public void testGive1() throws Exception {
        Map<Chip, Integer> chips = new HashMap<>();
        chips.put(Chip.BLACK_100, 5);
        chips.put(Chip.BROWN_10000, 2);

        ChipStack cs = new ChipStack(chips);

        assertEquals(20_000, cs.give(20_000));
    }

    @Test
    public void testGive2() throws Exception {
        // Test if the "persistence" layer is updated
        // And if the upper chip value are given
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGiveException() throws Exception {
        Map<Chip, Integer> chips = new HashMap<>();
        chips.put(Chip.GREEN_25, 1);

        // If the has only 25 and we want 500, an exception should be thrown
        ChipStack cs = new ChipStack(chips);
        cs.give(500);
    }

    @Test
    public void testGiveForValue1() throws Exception {
        ChipStack cs = new ChipStack(null);
        assertEquals(cs.giveForValue(50, 200, 200), 200);
    }

    @Test
    public void testGiveForValue2() throws Exception {
        ChipStack cs = new ChipStack(null);
        assertEquals(cs.giveForValue(50, 200, 1), 50);
    }

    @Test
    public void testGiveForValue3() throws Exception {
        ChipStack cs = new ChipStack(null);
        assertEquals(cs.giveForValue(50, 200, 4), 200);
    }

    @Test
    public void testUpdateChipNumberForValue() throws Exception {
        Map<Chip, Integer> chips = new HashMap<>();
        chips.put(Chip.BLACK_100, 5);
        chips.put(Chip.BROWN_10000, 2);

        ChipStack cs = new ChipStack(chips);

        cs.give(20_000);
        chips = cs.chips;

        Object i = chips.get(Chip.BLACK_100);
        assertEquals(0, i);

        i = chips.get(Chip.BLACK_100);
        assertEquals(0, i);
    }

    @Test
    public void testGetMoneyAmount1() throws Exception {
        Map<Chip, Integer> chips = new HashMap<>();
        chips.put(Chip.BLACK_100, 5);
        chips.put(Chip.BROWN_10000, 2);

        ChipStack cs = new ChipStack(chips);

        assertEquals(cs.getMoneyAmount(), 2 * 10_000 + 5 * 100);
    }

    @Test
    public void testGetMoneyAmount2() throws Exception {
        ChipStack cs = new ChipStack(null);

        assertEquals(cs.getMoneyAmount(), 0);
    }
}