package fr.damienraymond.pocker;

/**
 * Created by damien on 01/10/2015.
 */
public class Button {

    protected Player buttonOwnerPlayer;

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
