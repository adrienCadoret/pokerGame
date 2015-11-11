package fr.damienraymond.pocker;

import fr.damienraymond.pocker.player.Player;
import fr.damienraymond.pocker.player.PlayerSimple;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by damien on 18/10/2015.
 */
public class ButtonTest {

    @Test
    public void testThisPlayerIsTheOwnerOfTheButtonTrue() throws Exception {
        Button button = new Button(null);
        Table table = new Table(button, new LinkedList<>());
        Player damien = new PlayerSimple("Damien", table);
        button.buttonOwnerPlayer = damien;
        assertTrue(button.thisPlayerIsTheOwnerOfTheButton(damien));
    }

    @Test
    public void testThisPlayerIsTheOwnerOfTheButtonFalse() throws Exception {
        Button button = new Button(null);
        Table table = new Table(button, new LinkedList<>());
        Player damien = new PlayerSimple("Damien", table);
        button.buttonOwnerPlayer = damien;
        assertFalse(button.thisPlayerIsTheOwnerOfTheButton(new PlayerSimple("Paul", table)));
    }

    @Test
    public void testGetButtonOwnerPlayer() throws Exception {
        Button button = new Button(null);
        Table table = new Table(button, new LinkedList<>());
        Player damien = new PlayerSimple("Damien", table);
        button.buttonOwnerPlayer = damien;
        assertEquals(damien, button.getButtonOwnerPlayer());
    }
}