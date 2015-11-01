package fr.damienraymond.pocker;

import fr.damienraymond.pocker.card.Card;

import java.util.Set;

/**
 * Created by damien on 01/11/2015.
 */
public class PokerSimple extends Poker {
    @Override
    protected Set<Chip> askPlayerToGive(Player p, int amountOfMoney) {
        return null;
    }

    @Override
    protected void giveChipsToPlayer(Player player, Set<Chip> chips) {

    }

    @Override
    protected void giveCardToPlayer(Set<Card> cards) {

    }

    @Override
    protected int askThePlayerToPlay(Player player, int bigBlindAmount, int amountToCall) {
        return 0;
    }

    @Override
    protected void shutdown(Player p) {

    }

    @Override
    protected void burnCard() {

    }

    @Override
    protected void putOneCardOnTheTable() {

    }
}
