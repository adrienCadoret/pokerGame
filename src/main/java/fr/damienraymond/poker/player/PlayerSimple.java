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

    public boolean canPay() {
        return true; // check money : for the moment let's guess the player is very rich
    }

    public Set<Chip> giveChip(int amount){
        this.chips.give(amount);
        return new HashSet<>();
    }

    public void receiveChipList(List<Chip> chips) {
        chips.forEach(this.chips::addChip);
    }

    public void receiveCards(List<Card> cards){
        cards.forEach(hand::addCard);
    }

    protected String getGameInfos(){
        String res = "GameInfos [";
        res += table.map(t -> {
            return t.getPlayers().stream().map(Player::toString);
        });
        return res + "]";
    }

    public int play(int bigBlindAmount, int amountToCall, boolean playerCanCheck) {

        Logger.info(this.getGameInfos());

        // List of the correct user inputs
        List<Integer> correctValues = new ArrayList<>(PlayerInput.getChoices(bigBlindAmount, amountToCall, playerCanCheck).values());

        Logger.trace("Correct values : " + correctValues);

        return UserInput.readAndValidateUserInput(correctValues, "Choices ? " + PlayerInput.choicesToString(bigBlindAmount, amountToCall, playerCanCheck));
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
}
