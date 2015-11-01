package fr.damienraymond.pocker;

import fr.damienraymond.pocker.card.Card;
import fr.damienraymond.pocker.card.PlayerCyclicIterator;
import fr.damienraymond.pocker.utils.CyclicIterator;
import fr.damienraymond.pocker.utils.RandomFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by damien on 23/10/2015.
 */
public abstract class Poker {

    abstract protected Set<Chip> askPlayerToGive(Player p, int amountOfMoney);
    abstract protected void giveChipsToPlayer(Player player, Set<Chip> chips);

    abstract protected void giveCardToPlayer(Set<Card> cards);
    abstract protected int askThePlayerToPlay(Player player, int bigBlindAmount, int amountToCall); // returns the player choice

    abstract protected void shutdown(Player p);

//    abstract protected void askPlayerToGiveSmallBlinds(Player player, int amount);
//    abstract protected void askPlayerToGiveBigBlinds();

    abstract protected void burnCard();
    abstract protected void putOneCardOnTheTable();

    public void poker() throws PokerException {

        Button button = new Button(null);
        Table table = new Table(button);

        int initialAmount = 20_000;

        List<Player> players = this.launch(table, button, initialAmount);

        if(players.isEmpty()) // TODO : check min player number
            throw new PokerException("Not enough players");

        final PlayerCyclicIterator playerCyclicIterator = new PlayerCyclicIterator(players);

        int bigBlindAmount = this.blinds(playerCyclicIterator, initialAmount);


        this.preFlop(playerCyclicIterator);

        this.flop(playerCyclicIterator, button.getButtonOwnerPlayer(), 3); // get rid of the 3 here, not business

        this.turn(playerCyclicIterator, button.getButtonOwnerPlayer());

        this.river(playerCyclicIterator, button.getButtonOwnerPlayer());

        this.shutdown(playerCyclicIterator, button);

    }

    protected List<Player> launch(Table table, Button button, int amount){
        // Player creation
        final List<String> names = Arrays.asList("Damien", "Pierre", "Paul", "Marie", "Clément");
        List<Player> players = this.playerCreation(table, names);

        // Button distribution
        button = this.buttonDistribution(players, button);

        this.chipDistribution(players, amount);

        return players;
    }

    protected void chipDistribution(List<Player> players, int amount) {
        Set<Chip> chips = this.getInitChipStackForPlayer(amount);
        players.forEach(player -> this.giveChipsToPlayer(player, chips));
    }

    protected Set<Chip> getInitChipStackForPlayer(final int amount){
        List<Integer> values = Chip.getAvailableValues();
        values = values
                .stream()
                .filter(e -> e <= amount)
                .collect(Collectors.toList());

        // Then sort and reverse the list
        Collections.sort(values);
        Collections.reverse(values);

        Set<Chip> chips = new HashSet<>();

        int numberOfChipForValue;
        int amountLoop = amount;
        for (Integer value : values) {
            numberOfChipForValue = amountLoop % value;
            IntStream.range(0, numberOfChipForValue)
                    .forEach(e -> chips.add(Chip.valueOf(value)));
            amountLoop = amountLoop % value;
        }
        return chips;
    }

    private Button buttonDistribution(List<Player> players, Button button) {
        int randomInt = RandomFactory.randInt(0, players.size() - 1);
        Player buttonOwner = players.get(randomInt);
        return new Button(buttonOwner);
    }


    protected List<Player> playerCreation(Table table, List<String> names){
        return names
                .stream()
                .map(name -> {
                    Player p = new Player(name, table);
                    table.addPlayerToTable(p);
                    return p;
                })
                .collect(Collectors.toList());
    }


    private int bigBlindAmount;

    protected int blinds(PlayerCyclicIterator players, int initialAmount){
        bigBlindAmount = initialAmount / 100;
        int smallBlindAmount = bigBlindAmount / 2;

        this.askPlayerToGive(players.next(), smallBlindAmount);
        this.askPlayerToGive(players.next(), bigBlindAmount);
        return bigBlindAmount;
    }


    private int playerCanPlayNumber;
    private int amountToCall;

    protected void preFlop(PlayerCyclicIterator players){
        final int totalPlayerNumber = players.number();
        playerCanPlayNumber = totalPlayerNumber;
        int hasPlayPlayerNumber = 0; // number of player that have to play. re-initialised on raises

        // Init memorisation of who is folded for the hand
        players.initFoldedPlayer();

        amountToCall = bigBlindAmount;
        boolean goOn = true;
        while(goOn){
            // Get next player around the table
            Player currentPlayer = players.next();

            // Check if the current player can play (not folded, and have enough money)
            if(players.thisPlayerHasFolded(currentPlayer) && currentPlayer.canPay()){

                // Ask to user the amount he wants to give
                //  O                             -> fold
                //  amountToCall                  -> call
                //  amountToCall + bigBlindAmount -> raise
                int amountThePlayerGive = this.askThePlayerToPlay(currentPlayer, bigBlindAmount, amountToCall);

                // Check id the player wants to fold
                if(amountThePlayerGive == 0) {
                    players.playerHasFold(currentPlayer);
                    playerCanPlayNumber -= 1;
                } else {
                    // Check of the player has raised
                    if(bigBlindAmount + amountToCall == amountThePlayerGive){
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

    private Player playerHasPlayedFirst = null;

    protected void flop(PlayerCyclicIterator players, Player buttonOwnerPlayer, int numberOfCardToReveal){
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
        while(goOn){
            // Get next player around the table
            Player currentPlayer = players.next();

            // Check if the current player can play (not folded, and have enough money)
            if(players.thisPlayerHasFolded(currentPlayer) && currentPlayer.canPay()){


                // Ask to user the amount he wants to give
                //  -1                            -> check
                //  0                             -> fold
                //  amountToCall                  -> call
                //  amountToCall + bigBlindAmount -> raise
                int amountThePlayerGive = this.askThePlayerToPlay(currentPlayer, bigBlindAmount, amountToCall);

                if(amountThePlayerGive == -1){
                    // in case of check
                }else{

                    canCheck = false;

                    // Check id the player wants to fold
                    if(amountThePlayerGive == 0) {
                        players.playerHasFold(currentPlayer);
                        playerCanPlayNumber -= 1;
                    } else {

                        // Memorize the first player to call
                        if(playerHasPlayedFirst == null)
                            playerHasPlayedFirst = currentPlayer;

                        // Check of the player has raised
                        if(bigBlindAmount + amountToCall == amountThePlayerGive){
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

    protected void turn(PlayerCyclicIterator playerCyclicIterator, Player buttonOwnerPlayer){
        this.flop(playerCyclicIterator, buttonOwnerPlayer, 1);
    }

    protected void river(PlayerCyclicIterator playerCyclicIterator, Player buttonOwnerPlayer){
        this.flop(playerCyclicIterator, buttonOwnerPlayer, 1);
    }

    protected void shutdown(PlayerCyclicIterator playerCyclicIterator, Button button){

        Player firstPlayerToRevealHisCards = null;

        if(playerHasPlayedFirst == null){
            // If no player has call (only checks)
            // We go to the player after the button
            playerCyclicIterator.cycleUntilAfterThisPlayer(button.buttonOwnerPlayer);
        } else {
            // Go to the first player who played
            playerCyclicIterator = playerCyclicIterator.dropWhile(p -> ! p.equals(playerHasPlayedFirst));
        }

        boolean goOn = true;
        while(goOn) {
            // Get next player around the table
            Player currentPlayer = playerCyclicIterator.next();

            if(firstPlayerToRevealHisCards == null)
                firstPlayerToRevealHisCards = currentPlayer;

            // If current player is not fold
            if (playerCyclicIterator.thisPlayerHasFolded(currentPlayer)){
                this.shutdown(currentPlayer);
            }

            // When the all the player has shown their cards
            goOn = firstPlayerToRevealHisCards == currentPlayer;
        }


    }


}
