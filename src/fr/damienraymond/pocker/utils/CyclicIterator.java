package fr.damienraymond.pocker.utils;

import fr.damienraymond.pocker.Player;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by damien on 25/10/2015.
 */
public class CyclicIterator<T> implements Iterator<T> {

    private final List<T> list;
    private Iterator<T> iterator;

    private Map<Player, Boolean> foldedPlayer;

    public CyclicIterator(List<T> list) {
        this.list = list;
        iterator = list.iterator();
        this.initFoldedPlayer();
    }

    @Override
    public boolean hasNext() {
        return ! list.isEmpty();
    }

    /**
     * WARNING : possibility of infinite call; always test with hasNext
     */
    @Override
    public T next() {
        if (iterator.hasNext())
            return iterator.next();
        else
            iterator = list.iterator();
            return this.next();
    }


    public List<T> take(int number){
        int i = 0;
        List<T> res = new LinkedList<>();
        while (i++ < number){
            if(this.hasNext())
                res.add(this.next());
        }
        return res;
    }

    public CyclicIterator<T> dropWhile(Predicate<? super T> predicate){
        while (this.hasNext()){
            if(! predicate.test(this.next()))
                break;
        }
        return this;
    }

    public List<T> takeListNumber() {
        return this.take(list.size());
    }

    public int number(){
        return list.size();
    }


    // Todo : add specialisation to this class business methods
    public void initFoldedPlayer(){
        this.foldedPlayer = new HashMap<>();
    }

    public void playerHasFold(Player p){
        this.foldedPlayer.put(p, true);
    }

    public boolean thisPlayerHasFolded(Player p){
        return this.foldedPlayer.getOrDefault(p, false);
    }

    public void cycleUntilAfterThisPlayer(Player p){
        Player currentPlayer;
        do {
            currentPlayer = (Player)this.next();
        }while (currentPlayer != p);

        // Consume one more to go after Player p
        this.next();
    }
}
