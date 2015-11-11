package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.CardPacket;
import fr.damienraymond.poker.card.CardPacketException;
import fr.damienraymond.poker.player.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 02/10/2015.
 */
public class Table {

    public Button button;
    public List<Player> players;
    /**
     * Give a random id to the table in case of usage of several ones
     */
    private UUID uuidTable;
    private List<Card> cardsOnTheTable;
    private CardPacket cardPacket;
    public Table(Button button, List<Player> players) {
        this.button = button;
        this.players = players;
        this.uuidTable = UUID.randomUUID();
        this.initCardOnTheTable();
        this.cardPacket = new CardPacket();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void addPlayerToTable(Player p){
        // First check if the player name is not already taken
        List<String> playerNames = getPlayerNames();
        playerNames.contains(p.getPlayerName());


    }

    public List<String> getPlayerNames(){
        return players.stream()
                .map(Player::getPlayerName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addCardOnTheTable(){
        try {
            Card c = cardPacket.popCard();
            cardsOnTheTable.add(c);
        } catch (CardPacketException e) {
            e.printStackTrace();
        }

    }

    public void initCardOnTheTable(){
        this.cardsOnTheTable = new LinkedList<>();
    }

    /**
     * Equal on uuid
     * @param o the object to test with `this`
     * @return true if uuid are equals; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        return uuidTable.equals(table.uuidTable);

    }

    @Override
    public int hashCode() {
        return uuidTable.hashCode();
    }

    public Table update() {
        return this;
    }
}
