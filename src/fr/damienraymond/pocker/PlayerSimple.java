package fr.damienraymond.pocker;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import fr.damienraymond.pocker.card.Card;
import fr.damienraymond.pocker.utils.UserInput;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 04/11/2015.
 */
public class PlayerSimple extends Player {

    protected PlayerSimple(String playerName, Table table) {
        super(playerName, table);
    }

    public boolean canPay() {
        return true; // check money : for the moment let's guess the player is very rich
    }

    public Set<Chip> giveChip(int amount){
        this.chips.give(amount);
        return new HashSet<>();
    }

    public void receiveChipSet(Set<Chip> chips) {
        chips.forEach(chips::add);
    }

    public void receiveCards(List<Card> cards){
        cards.forEach(hand::addCard);
    }

    public int play(int bigBlindAmount, int amountToCall, boolean playerCanCheck) {

        // List of the correct user inputs
        List<Integer> correctValues = new ArrayList<>(PlayerInput.getChoices(bigBlindAmount, amountToCall, playerCanCheck).values());

        return UserInput.readAndValidateUserInput(correctValues, "Choices ? " + PlayerInput.choicesToString(bigBlindAmount, amountToCall, playerCanCheck));
    }

    public List<Card> shutdown() {
        List<Card> card = hand.getCards();
        hand.empty();
        return card;
    }




}
