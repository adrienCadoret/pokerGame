package fr.damienraymond.poker;

import fr.damienraymond.poker.card.Card;
import fr.damienraymond.poker.card.CardPacket;
import fr.damienraymond.poker.card.PlayerCyclicIterator;
import fr.damienraymond.poker.chip.Chip;
import fr.damienraymond.poker.chip.ChipUtils;
import fr.damienraymond.poker.observer.Subject;
import fr.damienraymond.poker.player.Player;
import fr.damienraymond.poker.player.PlayerSimple;
import fr.damienraymond.poker.utils.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 23/10/2015.
 */
public abstract class Poker extends Subject {


    protected Table table;
    private Button button;
    private CardPacket cardPacket;
    private Player playerHasPlayedFirst = null;
    private int amountToCall;
    private int bigBlindAmount;
    private boolean canCheck = true;
    private int initialAmount;

    abstract protected void askPlayerToGive(Player p, int amountOfMoney);

    abstract protected void giveChipsToPlayer(Player player, List<Chip> chips);

    abstract protected void giveCardToPlayer(Player player, List<Card> cards);

    abstract protected int askThePlayerToPlay(Player player, int amountToCall, int amountToRaise, boolean canCheck); // returns the player choice

    abstract protected List<Card> shutdown(Player p);

    abstract protected void burnCard();

    abstract protected void putOneCardOnTheTable();

    public void poker(List<String> playerNames) throws PokerException {

        Logger.info("Welcome ! Let's play Poker !");

        initialAmount = 20_000;

        Logger.info("Launching...");
        List<Player> players = this.launch(table, playerNames, initialAmount);
        Logger.info("Successful launch...");

        if (players.isEmpty()) // TODO : check min player number
            throw new PokerException("Not enough players");

        final PlayerCyclicIterator playerCyclicIterator = new PlayerCyclicIterator(players);

        do {
            Logger.info("Blinds");
            this.blinds(playerCyclicIterator, initialAmount);

            Logger.info("Pre-flop");
            this.preFlop(playerCyclicIterator);

            Logger.info("Flop");
            this.flop(playerCyclicIterator, 3); // get rid of the 3 here, not business

            Logger.info("Turn");
            this.turn(playerCyclicIterator);

            Logger.info("River");
            this.river(playerCyclicIterator);

            Logger.info("Shutdown");
            Map<Player, List<Card>> shutdownRes = this.shutdown(playerCyclicIterator, button);

            Logger.info("Results");
            Player winner = this.result(players, shutdownRes);

            Logger.info("Manage winner");
            this.manageWinner(winner);

        }while (this.stopGameCondition());

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

        // Card distribution
        this.cardDistribution(players);

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
     * Distribute cards to players
     * @param players player list
     */
    protected void cardDistribution(List<Player> players){
        // Init card packet
        cardPacket = new CardPacket();

        // For each player give 2 cards
        players.forEach(player -> {
            List<Card> cards = Arrays.asList(cardPacket.popCard(), cardPacket.popCard());
            this.giveCardToPlayer(player, cards);
        });
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
        // Call of getChipsListFromAmount for each player, to prevent same memory pointer for chips
        // In the future it could be better to avoid this and to clone the chips set
        players.forEach(player -> this.giveChipsToPlayer(player, ChipUtils.getChipsListFromAmount(this.determineBlindAmounts(this.initialAmount),amount)));
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
        players.cycleUntilAfterThisPlayer(button.buttonOwnerPlayer);

        // Init amount on the table for each player
        players.initAmountOnTheTableForEachPlayer();

        this.askPlayersToGiveBlinds(players, smallBlindAmount, bigBlindAmount);
    }

    /**
     * Determine the blinds amounts
     * @param initialAmount the initial amount
     * @return small blind amount
     */
    protected int determineBlindAmounts(int initialAmount){
        bigBlindAmount = determineBigBlindAmount(initialAmount);
        return bigBlindAmount / 2;
    }

    protected int determineBigBlindAmount(int initialAmount){
        return initialAmount / 100;
    }


    /**
     * Ask players to give blinds
     * @param players the cyclic iterator of player
     * @param smallBlind the small blind amount
     * @param bigBlind the big blind amount
     */
    protected void askPlayersToGiveBlinds(PlayerCyclicIterator players, int smallBlind, int bigBlind) {
        Player p = players.next();
        players.addAmountOnTheTableForPlayer(p, smallBlind);
        this.askPlayerToGive(p, smallBlind);

        p = players.next();
        players.addAmountOnTheTableForPlayer(p, bigBlind);
        this.askPlayerToGive(p, bigBlind);
    }


    /**
     *******************************************************************************************************************
     *****************************************            PRE-FLOP            ******************************************
     *******************************************************************************************************************
     */


    protected void preFlop(PlayerCyclicIterator players) {

        this.beforePreFlop(players);

        boolean enableChecks = false;
        this.betPlayerInteractionTour(players, enableChecks, Optional.of(1));
    }

    protected void beforePreFlop(PlayerCyclicIterator players){
        // Init memorisation of who is folded for the hand
        players.initFoldedPlayer();

        // Initialize amountToCall to bigBlindAmount
        amountToCall = bigBlindAmount;

    }

    /**
     *******************************************************************************************************************
     *****************************************              FLOP              ******************************************
     *******************************************************************************************************************
     */


    protected void flop(PlayerCyclicIterator players, int numberOfCardToReveal) {
        this.betTour(players, numberOfCardToReveal);
    }

    protected void betTour(PlayerCyclicIterator players, int numberOfCardToReveal){
        // Burn the card
        this.burnCard();

        // Reveals 3 card on the table
        this.revealNCard(numberOfCardToReveal);

        // Cycle until player after button player
        this.cycleUntilAfterTheButtonPlayer(players);

        boolean enableChecks = true;
        this.betPlayerInteractionTour(players, enableChecks, Optional.empty());
    }

    protected void revealNCard(int numberOfCardToReveal){
        for (int i = 0; i < numberOfCardToReveal; i++) {
            this.putOneCardOnTheTable();
        }
    }

    protected void cycleUntilAfterTheButtonPlayer(PlayerCyclicIterator players){
        Logger.trace("Cycle until after the button player");
        players.cycleUntilAfterThisPlayer(this.button.getButtonOwnerPlayer());
    }

    protected void betPlayerInteractionTour(PlayerCyclicIterator players, boolean enableCheck, Optional<Integer> hasPlayPlayerNumberOption){

        // Initialize vars
        playerHasPlayedFirst = null; // Restart var playerHasPlayedFirst (beginning of flop, turn, river)
        boolean goOn = true;

        canCheck = true;

        while (goOn) {
            // Get next player around the table
            Player currentPlayer = players.next();

            this.bet(players, currentPlayer, enableCheck && canCheck);

            // Check end loop condition
            goOn = endCondition(players);
        }

        // Add all player bet to the table
        table.addAmountOnTheTable(players.getAmountOnTheTable());
        // Reinit amount for each player
        players.initAmountOnTheTableForEachPlayer();
    }

    protected boolean endCondition(PlayerCyclicIterator players){
        return ! (
                players.testIfAllPlayersHasTheSameAmountOnTheTable() &&
                        players.testIfAllPlayerHasPlayed()
        );
    }

    protected void bet(PlayerCyclicIterator players, Player currentPlayer, boolean enableCheck){

        Logger.info("================   NEXT PLAYER   ================");
        Logger.info("Current player : " + players.toString(currentPlayer, table));
        Logger.info("Amount on the table : " + this.table.getAmountOnTheTable());
        Logger.info("CardsOnTheTable : " + this.table.getCardsOnTheTable());

        // Check if the current player can play (not folded, and have enough money)
        if (playerCanPlay(players, currentPlayer)) {
            int amountThePlayerGive = this.playerChoice(players, currentPlayer, enableCheck);

            this.managePlayerChoice(players, currentPlayer, amountThePlayerGive, enableCheck);
        }else{
            Logger.info(currentPlayer + " can't play !");
        }
    }

    protected boolean playerCanPlay(PlayerCyclicIterator players, Player p){
        return (! players.thisPlayerHasFolded(p)) && p.canPay(this.amountToCall);
    }

    protected int playerChoice(PlayerCyclicIterator players, Player p, boolean enableCheck){
        // If the player has already put amount on the table (in case of small blind for example)
        Optional<Integer> amountOnTheTableForPlayer = players.getAmountOnTheTableForPlayer(p);
        int amountToRaise = amountToCall * 2;
        int amountToCall = this.amountToCall;
        if(amountOnTheTableForPlayer.isPresent()){
            amountToRaise -= amountOnTheTableForPlayer.get();
            amountToCall -= amountOnTheTableForPlayer.get();
        }
        return this.askThePlayerToPlay(p, amountToCall, amountToRaise, enableCheck);
    }

    protected void managePlayerChoice(PlayerCyclicIterator players, Player currentPlayer, int amountThePlayerGive, boolean enableCheck){
        final int CHECK = -1;
        final int FOLD  = 0;

        players.addAmountOnTheTableForPlayer(currentPlayer, amountThePlayerGive);


        // Check if the player wants to fold
        if(amountThePlayerGive == FOLD){ // fold
            this.manageFoldChoice(players, currentPlayer);
        } else if(amountThePlayerGive == CHECK && enableCheck) { // check
            this.manageCheckChoice(currentPlayer);
        } else {

            this.memorizeFirstPlayerToCall(currentPlayer);

            int amountThePlayerHasOnTheTable = players.getAmountOnTheTableForPlayer(currentPlayer).get();

            // Check of the player has raised
            // The player has raised if he doubled the `amountToCall`
            if (amountThePlayerHasOnTheTable >= amountToCall * 2) {
                this.manageRaiseChoice(currentPlayer, amountThePlayerHasOnTheTable);
            }else if(amountThePlayerHasOnTheTable == amountToCall){
                this.manageCallChoice(currentPlayer);
            }
            amountToCall = amountThePlayerHasOnTheTable;
        }
    }

    protected void memorizeFirstPlayerToCall(Player currentPlayer){
        // Memorize the first player to call
        if (playerHasPlayedFirst == null)
            playerHasPlayedFirst = currentPlayer;
    }

    protected void manageFoldChoice(PlayerCyclicIterator players, Player currentPlayer){
        Logger.info(currentPlayer + " has folded.");
        players.playerHasFold(currentPlayer);
    }

    protected void manageCheckChoice(Player currentPlayer){
        Logger.info(currentPlayer + " has checked.");
    }
    protected void manageCallChoice(Player currentPlayer){
        canCheck = false;
        Logger.info(currentPlayer + " has called.");
    }
    protected void manageRaiseChoice(Player currentPlayer, int amountThePlayerHasOnTheTable){
        canCheck = false;
        Logger.info(currentPlayer + " has raised.");
    }

    /**
     *******************************************************************************************************************
     *****************************************              TURN              ******************************************
     *******************************************************************************************************************
     */

    protected void turn(PlayerCyclicIterator playerCyclicIterator) {
        this.betTour(playerCyclicIterator, 1);
    }

    /**
     *******************************************************************************************************************
     *****************************************              RIVER             ******************************************
     *******************************************************************************************************************
     */

    protected void river(PlayerCyclicIterator playerCyclicIterator) {
        this.betTour(playerCyclicIterator, 1);
    }

    /**
     *******************************************************************************************************************
     *****************************************            SHUTDOWN            ******************************************
     *******************************************************************************************************************
     */

    protected Map<Player, List<Card>> shutdown(PlayerCyclicIterator playerCyclicIterator, Button button) {

        int hasShutdownNumber = 0;

        Map<Player, List<Card>> playersCard = new HashMap<>();

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

            hasShutdownNumber++;

            // If current player is not fold
            if (! playerCyclicIterator.thisPlayerHasFolded(currentPlayer)) {
                List<Card> cards = this.shutdown(currentPlayer);
                playersCard.put(currentPlayer, cards);
                Logger.info(currentPlayer + " -> " + cards);
            }

            // When the all the player has shown their cards
            goOn = hasShutdownNumber < table.getPlayers().size();
        }

        return playersCard;
    }

    /**
     *******************************************************************************************************************
     *****************************************             RESULT             ******************************************
     *******************************************************************************************************************
     */

    private Player result(List<Player> players, Map<Player, List<Card>> shutdownRes) {

        Map<Hand, Player> playerHands = new HashMap<>();

        players.stream().forEach(player -> {
            List<Card> playerCards = shutdownRes.get(player);
            Set<Hand> collected = new HashSet<>();
            if (playerCards != null) {

                // Create a set of 7 cards
                Set<Card> cards = new HashSet<>();
                cards.addAll(playerCards);
                cards.addAll(this.table.getCardsOnTheTable());

                // Then create combinations of `cards` of size 5
                Set<Set<Card>> combination = Combination.combination(cards, 5);

                // Create good structure
                collected = combination.stream()
                        .map(hand -> new Hand(new ArrayList<>(hand)))
                        .collect(Collectors.toSet());
            }
            Hand bestHand = HandUtils.findBestHand(new NonEmptySet<>(collected));
            playerHands.put(bestHand, player);
        });


        Hand bestHand = HandUtils.findBestHand(new NonEmptySet<>(playerHands.keySet()));
        Logger.info("Best hand : " + Hand.getHandType(bestHand) + " (" + bestHand + ")");
        Player winner = playerHands.get(bestHand);
        Logger.info("Winner : " + winner);
        return winner;
    }

    private void manageWinner(Player winner) {
        this.giveChipsToPlayer(winner, ChipUtils.getChipsListFromAmount(this.determineBlindAmounts(this.initialAmount), this.table.getAmountOnTheTable()));
        this.table.initAmountOnTheTable();
    }

    private boolean stopGameCondition() {
        return this.table.getPlayers().stream()
                .filter(p -> p.canPay(this.determineBigBlindAmount(this.initialAmount))) // Player can play only if he has more than the bigbling amount for the next game
                .count() >= 2;
    }


}
