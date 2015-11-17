package fr.damienraymond.poker.player;

import fr.damienraymond.poker.chip.Chip;
import fr.damienraymond.poker.Table;
import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.utils.Logger;
import fr.damienraymond.poker.utils.UserInput;

import java.util.*;

/**
 * Created by damien on 04/11/2015.
 */
public class PlayerSimple extends Player {

    public PlayerSimple(String playerName, Table table) {
        super(playerName, table);
    }

    public PlayerSimple(String playerName) {
        super(playerName);
    }

    public boolean canPay(int amountToCall) {
        return this.chips.getMoneyAmount() >= amountToCall;
    }

    public void giveChip(int amount){
        Logger.trace("giveChip(" + amount + ")");
        this.chips.give(amount);
    }

    public void receiveChipList(List<Chip> chips) {
        chips.forEach(this.chips::addChip);
    }

    public void receiveCards(List<Card> cards){
        cards.forEach(hand::addCard);
    }

    protected String getGameInfos(){
        StringBuilder res = new StringBuilder();
        res.append("GameInfos [");
        table.ifPresent(t -> {
            res.append("Chip values : ")
                    .append(this.chips.getMoneyAmount())
                    .append(" | ");
            res.append("Cards(")
                    .append(this.hand.toString())
                    .append(")");
        });
        res.append("]");
        return res.toString();
    }

    public int play(int amountToCall, int amountToRaise, boolean playerCanCheck) {

        Logger.info(this.getGameInfos());

        // List of the correct user inputs
        List<Integer> correctValues = new ArrayList<>(PlayerInput.getChoices(amountToCall, playerCanCheck).values());

        Logger.trace("Correct values : " + correctValues);

        Integer res = UserInput.readAndValidateUserInput(correctValues, amountToRaise, this.chips.getMoneyAmount(), "Choices ? " + PlayerInput.choicesToString(amountToCall, amountToRaise, playerCanCheck));

        // Update chip stack
        this.giveChip(res);

        return res;
    }

    public List<Card> shutdown() {
        List<Card> card = hand.getCards();
        hand.empty();
        return card;
    }


    @Override
    public void update() {
        // I know this is useless for the moment
        //  but I bet that later it will be
        table.ifPresent(t -> table = Optional.of(t.update()));
    }

    @Override
    public String toString() {
        return this.playerName;
    }
}
