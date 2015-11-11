package fr.damienraymond.pocker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by damien on 02/10/2015.
 */
public class Table {

    public Button button;
    public List<Player> players;
    /**
     * Give a random id to the table in case of usage of several ones
     */
    private UUID uuidTable;


    public Table(Button button) {
        this.button = button;
        this.uuidTable = UUID.randomUUID();
    }

    public Button getButton() {
        return button;
    }

    public void addPlayerToTable(Player p){
        // First check if the player name is not already taken
        List<String> playerNames = getPlayerNames();
        playerNames.contains(p.getPlayerName());


    }


    public List<String> getPlayerNames(){
        return players.stream()
                .map(Player::getPlayerName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Equal on uuid
     * @param o the object to test with `this`
     * @return true if uuid are equals; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        return uuidTable.equals(table.uuidTable);

    }

    @Override
    public int hashCode() {
        return uuidTable.hashCode();
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
