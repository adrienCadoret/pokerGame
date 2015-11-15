package fr.damienraymond.poker.card;

import fr.damienraymond.poker.player.Player;
import fr.damienraymond.poker.utils.CyclicIterator;
import fr.damienraymond.poker.utils.Logger;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by damien on 01/11/2015.
 */
public class PlayerCyclicIterator extends CyclicIterator<Player> {

    private Map<Player, Boolean> foldedPlayer;
    private Map<Player, Integer> amountOnTheTableForEachPlayer;

    public PlayerCyclicIterator(List<Player> list) {
        super(list);
        this.initFoldedPlayer();
    }

    public void initFoldedPlayer() {
        this.foldedPlayer = new HashMap<>();
    }

    public void playerHasFold(Player p) {
        this.foldedPlayer.put(p, true);
    }

    public boolean thisPlayerHasFolded(Player p) {
        return this.foldedPlayer.getOrDefault(p, false);
    }

    public void initAmountOnTheTableForEachPlayer(){
        this.amountOnTheTableForEachPlayer = new HashMap<>();
    }

    public void addAmountOnTheTableForPlayer(Player p, int amount){
        int previousAmount = this.getAmountOnTheTableForPlayer(p).orElse(0);
        this.amountOnTheTableForEachPlayer.put(p, previousAmount + amount);
    }

    public Optional<Integer> getAmountOnTheTableForPlayer(Player p){
        Integer amount = this.amountOnTheTableForEachPlayer.get(p);
        if(amount == null){
            return Optional.empty();
        }else{
            return Optional.of(amount);
        }
    }

    public boolean testIfAllPlayersHasTheSameAmontOnTheTable(){
        return new HashSet<Integer>(this.amountOnTheTableForEachPlayer.values()).size() == 1;
    }

    public void cycleUntilAfterThisPlayer(Player player) {
        this.dropWhile(p -> ! p.equals(player));
    }

    @Override
    public PlayerCyclicIterator dropWhile(Predicate<? super Player> predicate) {
        // Ugly but necessary
        // because of the covariance of types
        // e.g : on inheritance, super.dropWhile return type is not replaced by PlayerCyclicIterator, see https://en.wikipedia.org/wiki/Covariant_return_type
        return (PlayerCyclicIterator) super.dropWhile(predicate);
    }

    public String toString(Player currentPlayer) {
        return this.list.stream().map(player -> {
            String res = "";
            if(currentPlayer.equals(player)){
                res += "{";
                res += player;
                res += "}";
            }else{
                res += player;
            }
            return res;
        }).collect(Collectors.joining(", "));
    }
}
