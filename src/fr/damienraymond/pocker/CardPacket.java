package fr.damienraymond.pocker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by damien on 02/10/2015.
 */
public class CardPacket {

    protected Iterator<Card> cards;


    public CardPacket() {

        this.cards = this.generateCardPacket();
    }


    public Iterator<Card> generateCardPacket(){
        List<Level> levels = new ArrayList<>(EnumSet.allOf(Level.class));
        List<Color> colors = new ArrayList<>(EnumSet.allOf(Color.class));
        List<Card> cards = this.cartesianProductToProduceCardPacket(levels, colors);

        // Use of several permutation
        int permutationNumber = 3;
        for (int i = 0; i < permutationNumber; i++) {
            cards = this.permute(cards);
        }

        return cards.iterator();
    }

    public List<Card> cartesianProductToProduceCardPacket(List<Level> levels, List<Color> colors) {
        return levels.stream()
                .flatMap(level ->
                                colors.stream()
                                        .map(color ->
                                                        new Card(level, color)
                                        )
                ).collect(Collectors.toCollection(LinkedList::new));
    }


    public List<Card> permute(List<Card> input) {
        // Clone collection to improve immutability
        ArrayList cards = (ArrayList)((ArrayList)input).clone();
        Collections.shuffle(cards);
        return cards;
    }


    public Card popCard() throws CardPacketException {
        if (! cards.hasNext())
            throw new CardPacketException("Empty packet");
        return cards.next();
    }


}
