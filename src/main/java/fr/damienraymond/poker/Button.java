package fr.damienraymond.poker;

import fr.damienraymond.poker.player.Player;
import fr.damienraymond.poker.utils.Logger;

/**
 * Created by damien on 01/10/2015.
 */
public class Button {

    protected Player buttonOwnerPlayer;

    public Button(Player buttonOwnerPlayer) {
        this.buttonOwnerPlayer = buttonOwnerPlayer;
        if(buttonOwnerPlayer != null)
            Logger.info(buttonOwnerPlayer.getPlayerName() + " is the button owner");
    }

    /**
     * This method is able to determine if a player is this who has the button
     * @param p the player on want to check
     * @return true if p has the button ; false otherwise
     */
    public boolean thisPlayerIsTheOwnerOfTheButton(Player p){
        return (buttonOwnerPlayer != null) && buttonOwnerPlayer.equals(p);
    }

    /**
     * Get the button owner player
     * @return the button owner player
     */
    public Player getButtonOwnerPlayer() {
        return buttonOwnerPlayer;
    }
}
