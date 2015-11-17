package fr.damienraymond.poker.utils;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by damien on 17/11/2015.
 *
 * Non empty set to avoid reduce to return Optionnal
 */
public class NonEmptySet<T> {

    private Set<T> set;

    public NonEmptySet(T e) {
        this.set = new HashSet<>();
        this.add(e);
    }

    public NonEmptySet(Set<T> s) throws InvalidStateException {
        if (s.isEmpty()){
            throw new InvalidStateException("playerHands.empty");
        }
        this.set = new HashSet<>();
        this.addAll(s);
    }


    /**
     * Interface of Set::add
     * @param e the element to add
     */
    public boolean add(T e){
        return this.set.add(e);
    }


    /**
     * Interface of Set::addAll
     * @param s the set to add
     */
    public void addAll(Set<T> s){
        this.set.addAll(s);
    }

    /**
     * Get one element
     *  As the set is not empty iterator.next() will not fail
     * @return an element of the NonEmptySet
     */
    public T getOneElement(){
        return this.set.iterator().next();
    }


    public Stream<T> stream() {
        return this.set.stream();
    }

}
