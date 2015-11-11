package fr.damienraymond.pocker.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damien on 11/11/2015.
 */
public abstract class Subject {

    protected List<Observer> observerList = new ArrayList<>();

    /**
     * Attach observer to notify on change
     * @param o observer to attach
     */
    public void attach(Observer o){
        observerList.add(o);
    }

    /**
     * Detach observer
     * @param o observer to detach
     */
    public void detach(Observer o){
        observerList.remove(o);
    }

    /**
     * Notify observers
     *  To be redefined in subclasse
     */
    public abstract void notifyObservers();
}
