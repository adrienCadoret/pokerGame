package fr.damienraymond.poker.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 15/11/2015.
 */
public class Combination {


    /**
     * Returns combination of input set of size k
     * @param input input set
     * @param k size of the combinations
     * @param <T> the type of element to play with
     * @return combination of input set of size k
     */
    public static <T> Set<Set<T>> combination(Set<T> input, int k){
        return powerSet(input).stream()
                .filter(e -> k == e.size())
                .collect(Collectors.toSet());
    }

    /**
     * Create the power set of a set
     * WARNING : this not contains empty set
     *
     *  Ex : input = {1,2,3}
     *  powerSet(input) -> {{1},{2},{3},{1,2},{2,3},{1,3},{1,2,3}}
     *
     * @param input input list
     * @param <T> the type of element to play with
     * @return the power set of the input list
     */
    public static <T> Set<Set<T>> powerSet(Set<T> input){
        // Create the base
        //  Ex : input = {1,2,3}
        //  this creates {{1},{2},{3}}
        Set<Set<T>> base = new HashSet<>();
        input.forEach(i -> {
            HashSet<T> integer = new HashSet<>();
            integer.add(i);
            base.add(integer);
        });

        // Create a copy of base
        Collection<Set<T>> baseCollection = Collections.unmodifiableSet(base);
        Set<Set<T>> res = new HashSet<>(baseCollection);

        for (int i = 1; i < input.size(); i++) {
            res = cartesianProduct(res, base);
        }
        return res;
    }

    /**
     * Create cartesian product of two sets
     *
     *  Ex1 : s = {1,2,3}
     *  cartesianProduct(s, s) -> {{1,2},{2,3},{1,3}}
     *
     *  Ex2 : sa = {{1,2},{2,3},{1,3}} ; sb = {1,2,3}
     *  cartesianProduct(sa, sb) -> {{1,2,3}}
     *
     * @param sa first set
     * @param sb second set
     * @param <T> the type of element to play with
     * @return cartesian product
     */
    public static <T> Set<Set<T>> cartesianProduct(Set<Set<T>> sa, Set<Set<T>> sb){
        Set<Set<T>> res = new HashSet<>();
        sa.stream().forEach(a -> {
            sb.stream().forEach(b -> {
                Set<T> entry = new HashSet<T>(a);
                entry.addAll(b);
                res.add(entry);
            });
        });
        return res;
    }

}
