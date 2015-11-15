package fr.damienraymond.poker.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by damien on 15/11/2015.
 */
public class CombinationTest {

    @Test
    public void testCombination() throws Exception {
        Set<Integer> integers = new HashSet<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);


        Set<Set<Integer>> expectedRes = new HashSet<>();
        Set<Integer> elements;

        elements = new HashSet<>();elements.add(1); elements.add(2);
        expectedRes.add(elements);

        elements = new HashSet<>();elements.add(2); elements.add(3);
        expectedRes.add(elements);

        elements = new HashSet<>();elements.add(1); elements.add(3);
        expectedRes.add(elements);
        assertEquals(expectedRes, Combination.combination(integers, 2));
    }

    @Test
    public void testPowerSet() throws Exception {

    }

    @Test
    public void testCartesianProduct() throws Exception {

    }
}