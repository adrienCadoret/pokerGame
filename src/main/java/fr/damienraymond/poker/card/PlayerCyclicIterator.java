package fr.damienraymond.poker.card;

import fr.damienraymond.poker.player.Player;
import fr.damienraymond.poker.utils.CyclicIterator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by damien on 01/11/2015.
 */
public class PlayerCyclicIterator extends CyclicIterator<Player> {

    private Map<Player, Boolean> foldedPlayer;

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

    public void cycleUntilAfterThisPlayer(Player p) {
        Player currentPlayer;
        do {
            currentPlayer = (Player) this.next();
        } while (! currentPlayer.equals(p));

        // Consume one more to go after Player p
        this.next();
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
