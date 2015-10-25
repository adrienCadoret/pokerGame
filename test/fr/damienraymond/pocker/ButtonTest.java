package fr.damienraymond.pocker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by damien on 18/10/2015.
 */
public class ButtonTest {

    @Test
    public void testThisPlayerIsTheOwnerOfTheButtonTrue() throws Exception {
        Table table = new Table();
        Player damien = new Player("Damien", table);
        Button button = new Button(null);
        button.buttonOwnerPlayer = damien;
        assertTrue(button.thisPlayerIsTheOwnerOfTheButton(damien));
    }

    @Test
    public void testThisPlayerIsTheOwnerOfTheButtonFalse() throws Exception {
        Table table = new Table();
        Player damien = new Player("Damien", table);
        Button button = new Button(null);
        button.buttonOwnerPlayer = damien;
        assertFalse(button.thisPlayerIsTheOwnerOfTheButton(new Player("Paul", table)));
    }

    @Test
    public void testGetButtonOwnerPlayer() throws Exception {
        Table table = new Table();
        Player damien = new Player("Damien", table);
        Button button = new Button(null);
        button.buttonOwnerPlayer = damien;
        assertEquals(damien, button.getButtonOwnerPlayer());
    }
}