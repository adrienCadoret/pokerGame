package fr.damienraymond.pocker;


/**
 * Created by damien on 02/10/2015.
 */
public class Table {

    /**
     * Give a random id to the table in case of usage of several ones
     */
    private UUID uuidTable;

    public Button button;

    public List<Player> players;

    public Button getButton() {
        return button;
    }


    public Table() {
        this.uuidTable = UUID.randomUUID();
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
}
