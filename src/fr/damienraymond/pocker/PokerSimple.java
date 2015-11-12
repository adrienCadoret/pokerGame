package fr.damienraymond.pocker;

import fr.damienraymond.pocker.card.Card;
import fr.damienraymond.pocker.chip.Chip;
import fr.damienraymond.pocker.observer.Observer;
import fr.damienraymond.pocker.player.Player;

import java.util.List;
import java.util.Set;

/**
 * Created by damien on 01/11/2015.
 */
public class PokerSimple extends Poker {
    @Override
    protected Set<Chip> askPlayerToGive(Player p, int amountOfMoney) {
        return p.giveChip(amountOfMoney);
    }

    @Override
    protected void giveChipsToPlayer(Player player, List<Chip> chips) {
        Logger.trace("giveChipsToPlayer(" + player.getPlayerName() + ", " + chips + ")");
        player.receiveChipList(chips);
    }

    @Override
    protected void giveCardToPlayer(Player player, List<Card> cards) {
        player.receiveCards(cards);
    }

    @Override
    protected int askThePlayerToPlay(Player player, int bigBlindAmount, int amountToCall, boolean canCheck) {
        return player.play(bigBlindAmount, amountToCall, canCheck);
    }

    @Override
    protected List<Card> shutdown(Player p) {
        return p.shutdown();
    }

    @Override
    protected void burnCard() {

    }

    @Override
    protected void putOneCardOnTheTable() {
        this.table.addCardOnTheTable();
    }

    @Override
    public void notifyObservers() {
        observerList.forEach(Observer::notify);
    }
}
