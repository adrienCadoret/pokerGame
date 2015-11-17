package fr.damienraymond.poker.card;

import fr.damienraymond.poker.Table;
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

        // Manage case of fold (-1)
        amount = (amount < 0) ? 0 : amount;
        this.amountOnTheTableForEachPlayer.put(p, previousAmount + amount);
    }

    public Optional<Integer> getAmountOnTheTableForPlayer(Player p){
        Integer amount = this.amountOnTheTableForEachPlayer.get(p);
        if(amount == null || amount == 0){
            return Optional.empty();
        }else{
            return Optional.of(amount);
        }
    }

    public boolean testIfAllPlayersHasTheSameAmountOnTheTable(){
        Set<Integer> collected = this.amountOnTheTableForEachPlayer.entrySet().stream()
                .filter(e -> !this.foldedPlayer.getOrDefault(e.getKey(), false)) // Don't take care of folded players
                .map(Map.Entry::getValue) // Get values
                .collect(Collectors.toSet());
        return collected.size() == 1;
    }

    public boolean testIfAllPlayerHasPlayed(){
        return this.amountOnTheTableForEachPlayer.size() == this.list.size();
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

    public String toString(Player currentPlayer, Table table) {
        return this.list.stream().map(player -> {
            String res = "";
            String tmp = "";
            tmp = player + " (" + this.amountOnTheTableForEachPlayer.getOrDefault(player, 0) + ")";

            // Button
            if(player.equals(table.getButton().getButtonOwnerPlayer())){
                tmp = "[B]" + tmp;
            }

            // Folded
            if(this.foldedPlayer.getOrDefault(player, false)){
                tmp = "---" + tmp + "---";
            }

            // Current player
            if(currentPlayer.equals(player)){
                res += "{{{ " + tmp + " }}}";
            }else{
                res += tmp;
            }
            return res;
        }).collect(Collectors.joining(", "));
    }

    public Integer getAmountOnTheTable() {
        return this.amountOnTheTableForEachPlayer.values().stream().mapToInt(Integer::intValue).sum();
    }

}
