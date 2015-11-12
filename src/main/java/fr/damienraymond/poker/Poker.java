package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.PlayerCyclicIterator;
import fr.damienraymond.poker.chip.Chip;
import fr.damienraymond.poker.chip.ChipUtils;
import fr.damienraymond.poker.observer.Subject;
import fr.damienraymond.poker.player.Player;
import fr.damienraymond.poker.player.PlayerSimple;
import fr.damienraymond.poker.utils.Logger;
import fr.damienraymond.poker.utils.RandomFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 23/10/2015.
 */
public abstract class Poker extends Subject {


    protected Table table;
    private Button button;
    private int amountToCall;
    private int bigBlindAmount;
    private int playerCanPlayNumber;
    private Player playerHasPlayedFirst = null;

//    abstract protected void askPlayerToGiveSmallBlinds(Player player, int amount);
//    abstract protected void askPlayerToGiveBigBlinds();

    abstract protected Set<Chip> askPlayerToGive(Player p, int amountOfMoney);

    abstract protected void giveChipsToPlayer(Player player, Set<Chip> chips);

    abstract protected void giveCardToPlayer(Player player, List<Card> cards);

    abstract protected int askThePlayerToPlay(Player player, int bigBlindAmount, int amountToCall, boolean canCheck); // returns the player choice

    abstract protected List<Card> shutdown(Player p);

    abstract protected void burnCard();

    abstract protected void putOneCardOnTheTable();

    public void poker(List<String> playerNames) throws PokerException {

        Logger.info("Welcome ! Let's play Poker !");

        int initialAmount = 20_000;

        Logger.info("Launching...");
        List<Player> players = this.launch(table, playerNames, initialAmount);
        Logger.info("Successful launch...");

        if (players.isEmpty()) // TODO : check min player number
            throw new PokerException("Not enough players");

        final PlayerCyclicIterator playerCyclicIterator = new PlayerCyclicIterator(players);

        Logger.info("Blinds");
        this.blinds(playerCyclicIterator, initialAmount);

        Logger.info("Pre-flop");
        this.preFlop(playerCyclicIterator);

        Logger.info("Flop");
        this.flop(playerCyclicIterator, button.getButtonOwnerPlayer(), 3); // get rid of the 3 here, not business

        Logger.info("Turn");
        this.turn(playerCyclicIterator, button.getButtonOwnerPlayer());

        Logger.info("River");
        this.river(playerCyclicIterator, button.getButtonOwnerPlayer());

        Logger.info("Shutdown");
        this.shutdown(playerCyclicIterator, button);

    }


    /**
     *******************************************************************************************************************
     *****************************************             LAUNCH             ******************************************
     *******************************************************************************************************************
     */


    /**
     * Manage the launch of the poker game
     * player creation
     * button distribution
     * chip distribution
     *
     * @param table         the table to witch player are seated
     * @param initialAmount the initial amount of the game
     * @return a list of the players created
     */
    protected List<Player> launch(Table table, List<String> names, int initialAmount) {
        // Player creation
        List<Player> players = this.playerCreation(names);


        // Button distribution
        this.buttonDistribution(players);
        this.table = new Table(this.button, players);

        // Player table assignation
        players = this.playerTableAssignments(players);

        // Attach players to observer list
        this.attachPlayerToObserverList(players);

        // Chip distribution
        this.chipDistribution(players, initialAmount);

        return players;
    }

    /**
     * Distribute the button to a random player
     *
     * @param players player list
     */
    private void buttonDistribution(List<Player> players) {
        int randomInt = RandomFactory.randInt(0, players.size() - 1);
        Player buttonOwner = players.get(randomInt);
        this.button = new Button(buttonOwner);
    }

    /**
     * Creation of players from a list of name
     *
     * @param names list of future player name
     * @return the created players
     */
    protected List<Player> playerCreation(List<String> names) {
        return names
                .stream()
                .map(PlayerSimple::new) // TODO : change to factory and/or dep inj.
                .collect(Collectors.toList());
    }

    /**
     * Assign player to the table
     *
     * @param players the players
     * @return return a list of updated players
     */
    protected List<Player> playerTableAssignments(List<Player> players) {
        return players
                .stream()
                .map(player -> {
                    player.setTable(this.table);
                    this.table.addPlayerToTable(player);
                    return player;
                })
                .collect(Collectors.toList());
    }


    protected void attachPlayerToObserverList(List<Player> players) {
        players.forEach(this::attach);
    }

    /**
     * Way to distribute chips to players according a specific amont
     *
     * @param players the players to give chips
     * @param amount  the amount that represent chips to give to player
     */
    protected void chipDistribution(List<Player> players, int amount) {
        // Call of getChipsSetFromAmount for each player, to prevent same memory pointer for chips
        // In the future it could be better to avoid this and to clone the chips set
        players.forEach(player -> this.giveChipsToPlayer(player, ChipUtils.getChipsSetFromAmount(amount)));
    }

    /**
     *******************************************************************************************************************
     *****************************************             BLINDS             ******************************************
     *******************************************************************************************************************
     */


    /**
     * Determine the blinds amounts and ask players after the button to give blinds (small and big)
     * @param players the cyclic iterator of player
     * @param initialAmount the initial amount
     */
    protected void blinds(PlayerCyclicIterator players, int initialAmount) {
        int smallBlindAmount = this.determineBlindAmounts(initialAmount);

        // Go to the button owner player
        players.cycleUntilAfterThisPlayer(button.getButtonOwnerPlayer());

        this.askPlayersToGiveBlinds(players, smallBlindAmount, bigBlindAmount);
    }

    /**
     * Determine the blinds amounts
     * @param initialAmount the initial amount
     * @return small blind amount
     */
    protected int determineBlindAmounts(int initialAmount){
        bigBlindAmount = initialAmount / 100;
        return bigBlindAmount / 2;
    }


    /**
     * Ask players to give blinds
     * @param players the cyclic iterator of player
     * @param smallBlind the small blind amount
     * @param bigBlind the big blind amount
     */
    protected void askPlayersToGiveBlinds(PlayerCyclicIterator players, int smallBlind, int bigBlind) {
        this.askPlayerToGive(players.next(), smallBlind);
        this.askPlayerToGive(players.next(), bigBlind);
    }


    /**
     *******************************************************************************************************************
     *****************************************            PRE-FLOP            ******************************************
     *******************************************************************************************************************
     */


    protected void preFlop(PlayerCyclicIterator players) {
        final int totalPlayerNumber = players.number();
        playerCanPlayNumber = totalPlayerNumber;
        int hasPlayPlayerNumber = 0; // number of player that have to play. re-initialised on raises

        // Init memorisation of who is folded for the hand
        players.initFoldedPlayer();

        amountToCall = bigBlindAmount;
        boolean goOn = true;
        while (goOn) {
            // Get next player around the table
            Player currentPlayer = players.next();

            // Check if the current player can play (not folded, and have enough money)
            if (players.thisPlayerHasFolded(currentPlayer) && currentPlayer.canPay()) {

                // Ask to user the amount he wants to give
                //  O                             -> fold
                //  amountToCall                  -> call
                //  amountToCall + bigBlindAmount -> raise
                int amountThePlayerGive = this.askThePlayerToPlay(currentPlayer, bigBlindAmount, amountToCall, false);

                // Check id the player wants to fold
                if (amountThePlayerGive == 0) {
                    players.playerHasFold(currentPlayer);
                    playerCanPlayNumber -= 1;
                } else {
                    // Check of the player has raised
                    if (bigBlindAmount + amountToCall == amountThePlayerGive) {
                        // In case of raise
                        hasPlayPlayerNumber = 0;
                    }
                    amountToCall = amountThePlayerGive;
                }
            }

            // Check end loop condition
            goOn = hasPlayPlayerNumber < playerCanPlayNumber; // dummy for the moment. To be checked
        }
    }

    /**
     *******************************************************************************************************************
     *****************************************              FLOP              ******************************************
     *******************************************************************************************************************
     */


    protected void flop(PlayerCyclicIterator players, Player buttonOwnerPlayer, int numberOfCardToReveal) {
        // Burn the card
        this.burnCard();

        // Reveals 3 card on the table
        for (int i = 0; i < numberOfCardToReveal; i++) {
            this.putOneCardOnTheTable();
        }


        // Cycle until player after button player
        players.cycleUntilAfterThisPlayer(buttonOwnerPlayer);

        // Restart var playerHasPlayedFirst (beginning of flop, turn, river)
        playerHasPlayedFirst = null;

        int hasPlayPlayerNumber = 0; // number of player that have to play. re-initialised on raises
        boolean canCheck = true;

        boolean goOn = true;
        while (goOn) {
            // Get next player around the table
            Player currentPlayer = players.next();

            // Check if the current player can play (not folded, and have enough money)
            if (players.thisPlayerHasFolded(currentPlayer) && currentPlayer.canPay()) {


                // Ask to user the amount he wants to give
                //  -1                            -> check
                //  0                             -> fold
                //  amountToCall                  -> call
                //  amountToCall + bigBlindAmount -> raise
                int amountThePlayerGive = this.askThePlayerToPlay(currentPlayer, bigBlindAmount, amountToCall, true);

                if (amountThePlayerGive == -1) {
                    // in case of check
                } else {

                    canCheck = false;

                    // Check id the player wants to fold
                    if (amountThePlayerGive == 0) {
                        players.playerHasFold(currentPlayer);
                        playerCanPlayNumber -= 1;
                    } else {

                        // Memorize the first player to call
                        if (playerHasPlayedFirst == null)
                            playerHasPlayedFirst = currentPlayer;

                        // Check of the player has raised
                        if (bigBlindAmount + amountToCall == amountThePlayerGive) {
                            // In case of raise
                            hasPlayPlayerNumber = 0;
                        }
                        amountToCall = amountThePlayerGive;
                    }
                }
            }

            // Check end loop condition
            goOn = hasPlayPlayerNumber < playerCanPlayNumber; // dummy for the moment. To be checked
        }

    }

    /**
     *******************************************************************************************************************
     *****************************************              TURN              ******************************************
     *******************************************************************************************************************
     */

    protected void turn(PlayerCyclicIterator playerCyclicIterator, Player buttonOwnerPlayer) {
        this.flop(playerCyclicIterator, buttonOwnerPlayer, 1);
    }

    /**
     *******************************************************************************************************************
     *****************************************              RIVER             ******************************************
     *******************************************************************************************************************
     */

    protected void river(PlayerCyclicIterator playerCyclicIterator, Player buttonOwnerPlayer) {
        this.flop(playerCyclicIterator, buttonOwnerPlayer, 1);
    }

    /**
     *******************************************************************************************************************
     *****************************************            SHUTDOWN            ******************************************
     *******************************************************************************************************************
     */

    protected void shutdown(PlayerCyclicIterator playerCyclicIterator, Button button) {

        Player firstPlayerToRevealHisCards = null;

        if (playerHasPlayedFirst == null) {
            // If no player has call (only checks)
            // We go to the player after the button
            playerCyclicIterator.cycleUntilAfterThisPlayer(button.buttonOwnerPlayer);
        } else {
            // Go to the first player who played
            playerCyclicIterator = playerCyclicIterator.dropWhile(p -> !p.equals(playerHasPlayedFirst));
        }

        boolean goOn = true;
        while (goOn) {
            // Get next player around the table
            Player currentPlayer = playerCyclicIterator.next();

            if (firstPlayerToRevealHisCards == null)
                firstPlayerToRevealHisCards = currentPlayer;

            // If current player is not fold
            if (playerCyclicIterator.thisPlayerHasFolded(currentPlayer)) {
                List<Card> cards = this.shutdown(currentPlayer);
            }

            // When the all the player has shown their cards
            goOn = firstPlayerToRevealHisCards == currentPlayer;
        }


    }


}
