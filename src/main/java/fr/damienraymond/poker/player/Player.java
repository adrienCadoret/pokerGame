package fr.damienraymond.poker.player;

import fr.damienraymond.poker.*;
import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.chip.Chip;
import fr.damienraymond.poker.chip.ChipStack;
import fr.damienraymond.poker.observer.Observer;
import fr.damienraymond.poker.utils.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by damien on 01/10/2015.
 */
public abstract class Player implements Observer {

    protected Optional<Table> table;

    protected ChipStack chips;
    protected Hand hand;
    protected String playerName;

    protected Player(String playerName, Table table) {
        this.init(playerName, Optional.of(table));
    }

    protected Player(String playerName) {
        this.init(playerName, Optional.empty());
    }

    protected void init(String playerName, Optional<Table> table){
        Logger.trace("Create player " + playerName);
        this.playerName = playerName;
        this.table = table;
        this.hand = new HandConcrete();
        this.chips = new ChipStack(new HashMap<>());
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

    public abstract void receiveChipList(List<Chip> chips);

    public abstract void receiveCards(List<Card> cards);

    public abstract int play(int bigBlindAmount, int amountToCall, boolean playerCanCheck);

    public abstract List<Card> shutdown();


    public void setTable(Table table) {
        this.table = Optional.of(table);
    }
}
