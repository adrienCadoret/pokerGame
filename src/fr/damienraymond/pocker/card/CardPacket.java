package fr.damienraymond.pocker.card;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by damien on 02/10/2015.
 */
public class CardPacket {

    protected Iterator<Card> cards;
    private int defaultPermutationNumber = 6;

    public CardPacket(int permutationNumber) {
        this.cards = this.generateCardPacket(permutationNumber);
    }

    public CardPacket(){
        this.cards = this.generateCardPacket(defaultPermutationNumber);
    }


    public Iterator<Card> generateCardPacket(int permutationNumber){
        List<Level> levels = new ArrayList<>(EnumSet.allOf(Level.class));
        List<Color> colors = new ArrayList<>(EnumSet.allOf(Color.class));
        List<Card> cards = this.cartesianProductToProduceCardPacket(levels, colors);

        // Use of several permutation
        for (int i = 0; i < permutationNumber; i++) {
            cards = this.permute(cards);
        }

        return cards.iterator();
    }

    public Iterator<Card> generateCardPacket(){
        return this.generateCardPacket(this.defaultPermutationNumber);
    }



    public List<Card> cartesianProductToProduceCardPacket(List<Level> levels, List<Color> colors) {
        return levels.stream()
                .flatMap(level ->
                                colors.stream()
                                        .map(color ->
                                                        new Card(level, color)
                                        )
                ).collect(Collectors.toCollection(ArrayList::new));
    }


    public List<Card> permute(List<Card> input) {
        // Clone collection to improve immutability
        ArrayList cards = (ArrayList)((ArrayList)input).clone();
        Collections.shuffle(cards);
        return cards;
    }

    private void thereIsNoCardLeftInThePacket() throws CardPacketException {
        // It throws an exception now but I think that, in the future, it could be a good idea to refill the packet. But I'am not sure that it's one of the rules.
        throw new CardPacketException("Empty packet");
    }


    public Card popCard() throws CardPacketException {
        if (! cards.hasNext())
            this.thereIsNoCardLeftInThePacket();
        return cards.next();
    }


}
