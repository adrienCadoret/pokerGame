package fr.damienraymond.poker;

import fr.damienraymond.poker.player.Player;
import fr.damienraymond.poker.player.PlayerSimple;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by damien on 23/10/2015.
 */
public class PlayerTest {

    @Test
    public void testGetPlayerName() throws Exception {
        String name = "Damien";
        Player player = new PlayerSimple(name);
        Button button = new Button(player);
        Table table = new Table(button, new LinkedList<>());
        player.setTable(table);

        assertEquals(name, player.getPlayerName());
    }

}