package fr.damienraymond.pocker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by damien on 23/10/2015.
 */
public class PlayerTest {

    @Test
    public void testGetPlayerName() throws Exception {
        String name = "Damien";
        Player player = new Player(name, new Table());
        assertEquals(name, player.getPlayerName());
    }

}