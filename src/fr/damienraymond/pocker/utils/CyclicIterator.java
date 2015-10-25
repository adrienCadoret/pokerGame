package fr.damienraymond.pocker.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by damien on 25/10/2015.
 */
public class CyclicIterator<T> implements Iterator<T> {

    private final List<T> list;
    private Iterator<T> iterator;

    public CyclicIterator(List<T> list) {
        this.list = list;
        iterator = list.iterator();
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
}
