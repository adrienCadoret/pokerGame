package fr.damienraymond.pocker;

import fr.damienraymond.pocker.card.Card;

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
    protected void giveChipsToPlayer(Player player, Set<Chip> chips) {
        player.receiveChipSet(chips);
    }

    @Override
    protected void giveCardToPlayer(Player player, List<Card> cards) {
        player.receiveCards(cards);
    }

    @Override
    protected int askThePlayerToPlay(Player player, int bigBlindAmount, int amountToCall) {
        return player.play(bigBlindAmount, amountToCall);
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

    }
}
