package fr.damienraymond.pocker;

/**
 * Created by damien on 01/10/2015.
 */
public class Player {

    private Table table;

    private ChipStack chips;
    private Hand hand;
    private String playerName;

    protected Player(String playerName, Table table) {
        this.playerName = playerName;
        this.table = table;
    }


    public String getPlayerName() {
        return playerName;
    }

    /**
     * Equals on table and player name
     * (e.g considering one table, all player has different names)
     * @param o the object to test with `this`
     * @return true if table and player name are equals; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!table.equals(player.table)) return false;
        return playerName.equals(player.playerName);

    }

    @Override
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + playerName.hashCode();
        return result;
    }
}
