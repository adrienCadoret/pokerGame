package fr.damienraymond.poker.chip;

import fr.damienraymond.poker.utils.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 02/10/2015.
 */
public class ChipStack {

    protected Map<Chip, Integer> chips;

    public ChipStack(Map<Chip, Integer> chips) {
        this.chips = chips;
    }

    public void addChip(Chip c){
        this.initChipStackIfNecessary();
        int number = this.chips.getOrDefault(c, 0);
        chips.put(c, number + 1);
    }


    /**
     * Player gives money (symbolic)
     * In fact, it chip stack is decreased of the value to give
     * It start from the max valued chip (e.g Chip.BROWN_10000)
     *
     * @param amount the amount the player has to give
     * @return the effective amout the player has given
     * @throws IllegalArgumentException : if the player cannot pay it throws an exception
     */
    public int give(int amount) throws IllegalArgumentException {

        Logger.trace("Chip stack money amount: " + getMoneyAmount());

        if (amount > getMoneyAmount())
            throw new IllegalArgumentException("Cannot give this amount of money");

        // Get values from chipStack, this gives values from different types of ship the player has (e.g : List(100, 500, 50, 10)
        List<Integer> availableValues = chips.keySet().stream().map(Chip::getValue).collect(Collectors.toCollection(ArrayList::new));

        // Then sort and reverse the list
        Collections.sort(availableValues);
        Collections.reverse(availableValues);

        int tmpAmount = amount;

        for (Integer value : availableValues) {
            int numberChipValueThePlayerHas = chips.getOrDefault(value, 0);
            tmpAmount -= this.giveForValue(value, tmpAmount, numberChipValueThePlayerHas);
        }
        return tmpAmount;
    }


    /**
     * Get the amount the player gives
     * Update it chip number `chips`
     *
     * @param value the chip value (level) to manage
     * @param amountToGive the amount to give
     * @param numberChipValueThePlayerHas the number of chip, the player has, according to this value
     * @return the amount the player gives
     */
    protected int giveForValue(Integer value, int amountToGive, int numberChipValueThePlayerHas) {

        int amountThePlayerGive;

        int numberChipValueNeeded =  amountToGive / value;

        // Define the number of chip that the player don't has to pay
        int deficiency = numberChipValueThePlayerHas - numberChipValueNeeded;

        if (deficiency >= 0) {
            // The player can handle the ask. He has enough chips to pay for this value.

            amountThePlayerGive = value * numberChipValueNeeded;

            this.updateChipNumberForValue(value, numberChipValueThePlayerHas - numberChipValueNeeded);
        } else {
            // The player hasn't got enough chip to reach the amount to give.

            amountThePlayerGive = value * numberChipValueThePlayerHas;

            this.updateChipNumberForValue(value, 0);
        }
        return amountThePlayerGive;
    }


    protected void updateChipNumberForValue(Integer value, int newValue) {
        this.initChipStackIfNecessary();
        chips.put(Chip.valueOf(value), newValue);
    }


    public void initChipStackIfNecessary(){
        if (chips == null)
            chips = new HashMap<>();
    }

    public int getMoneyAmount(){
        this.initChipStackIfNecessary();
        return chips.entrySet().stream().mapToInt(e -> e.getValue() * e.getKey().getValue()).sum();
    }
}
