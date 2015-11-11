package fr.damienraymond.pocker.player;

import fr.damienraymond.pocker.*;
import fr.damienraymond.pocker.card.Card;
import fr.damienraymond.pocker.chip.Chip;
import fr.damienraymond.pocker.chip.ChipStack;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by damien on 01/10/2015.
 */
public abstract class Player {

    protected Optional<Table> table;

    protected ChipStack chips;
    protected Hand hand;
    protected String playerName;

    protected Player(String playerName, Table table) {
        this.playerName = playerName;
        this.table = Optional.of(table);
        this.hand = new HandConcrete();
    }

    protected Player(String playerName) {
        this.playerName = playerName;
        this.table = Optional.empty();
        this.hand = new HandConcrete();
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

    public abstract boolean canPay();

    public abstract Set<Chip> giveChip(int amount);

    public abstract void receiveChipSet(Set<Chip> chips);

    public abstract void receiveCards(List<Card> cards);

    public abstract int play(int bigBlindAmount, int amountToCall, boolean playerCanCheck);

    public abstract List<Card> shutdown();


    public void setTable(Table table) {
        this.table = Optional.of(table);
    }
}
