package fr.damienraymond.poker.chip;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by damien on 12/11/2015.
 */
public class ChipUtilsTest {

    @Test
    public void testGetChipsSetFromAmount1() throws Exception {
        assertArrayEquals(new Chip[]{}, ChipUtils.getChipsListFromAmount(0).toArray());
    }

    @Test
    public void testGetChipsSetFromAmount2() throws Exception {
        assertArrayEquals(new Chip[]{}, ChipUtils.getChipsListFromAmount(20).toArray());
    }

    @Test
    public void testGetChipsSetFromAmount3() throws Exception {
        assertArrayEquals(new Chip[]{Chip.GREEN_25}, ChipUtils.getChipsListFromAmount(25).toArray());
    }

    @Test
    public void testGetChipsSetFromAmount4() throws Exception {
        assertArrayEquals(new Chip[]{Chip.BLACK_100, Chip.BLUE_50}, ChipUtils.getChipsListFromAmount(150).toArray());
    }

    @Test
    public void testGetChipsSetFromAmount5() throws Exception {
        assertArrayEquals(new Chip[]{Chip.BROWN_10000, Chip.BROWN_10000}, ChipUtils.getChipsListFromAmount(20_000).toArray());
    }
}