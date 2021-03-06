package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.chip.Chip;
import fr.damienraymond.poker.observer.Observer;
import fr.damienraymond.poker.player.Player;
import fr.damienraymond.poker.utils.Logger;

import java.util.List;
import java.util.Set;

/**
 * Created by damien on 01/11/2015.
 */
public class PokerSimple extends Poker {
    @Override
    protected void askPlayerToGive(Player p, int amountOfMoney) {
        Logger.trace("askPlayerToGive(" + p.getPlayerName() + ", " + amountOfMoney + ")");
        p.giveChip(amountOfMoney);
    }

    @Override
    protected void giveChipsToPlayer(Player player, List<Chip> chips) {
        Logger.trace("giveChipsToPlayer(" + player.getPlayerName() + ", " + chips + ")");
        player.receiveChipList(chips);
    }

    @Override
    protected void giveCardToPlayer(Player player, List<Card> cards) {
        Logger.trace("giveCardToPlayer(" + player.getPlayerName() + ", " + cards + ")");
        player.receiveCards(cards);
    }

    @Override
    protected int askThePlayerToPlay(Player player, int amountToCall, int amountToRaise, boolean canCheck) {
        Logger.trace("askThePlayerToPlay(" + player.getPlayerName() + ")");
        return player.play(amountToCall, amountToRaise, canCheck);
    }

    @Override
    protected List<Card> shutdown(Player p) {
        Logger.trace("shutdown(" + p.getPlayerName() + ")");
        return p.shutdown();
    }

    @Override
    protected void burnCard() {
        Logger.trace("burnCard()");
    }

    @Override
    protected void putOneCardOnTheTable() {
        Logger.trace("putOneCardOnTheTable()");
        this.table.addCardOnTheTable();
    }

    @Override
    public void notifyObservers() {
        observerList.forEach(Observer::notify);
    }
}
