package fr.damienraymond.poker.card;

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

    /**
     * Generate card packet
     *  create cards
     *  permute permutationNumber times the packet
     * @param permutationNumber discribe the number of permutation
     * @return return cards
     */
    protected Iterator<Card> generateCardPacket(int permutationNumber){
        List<Level> levels = new ArrayList<>(EnumSet.allOf(Level.class));
        List<Color> colors = new ArrayList<>(EnumSet.allOf(Color.class));
        List<Card> cards = this.cartesianProductToProduceCardPacket(levels, colors);

        // Use of several permutation
        for (int i = 0; i < permutationNumber; i++) {
            cards = this.permute(cards);
        }

        return cards.iterator();
    }

    /**
     * Produce a list of card from the product of level (2,3,...,AS) and colors
     * @param levels the level list
     * @param colors the color list
     * @return a list of 52 (card(level) + card(colors)) card
     */
    protected List<Card> cartesianProductToProduceCardPacket(List<Level> levels, List<Color> colors) {
        return levels.stream()
                .flatMap(level ->
                                colors.stream()
                                        .map(color ->
                                                        new Card(level, color)
                                        )
                ).collect(Collectors.toCollection(ArrayList::new));
    }


    /**
     * Permute cards of the packet
     * @param input the cards to permute
     * @return permuted cards
     */
    protected List<Card> permute(List<Card> input) {
        // Clone collection to improve immutability
        ArrayList cards = (ArrayList)((ArrayList)input).clone();
        Collections.shuffle(cards);
        return cards;
    }

    /**
     * Manage errors when no card is left on the packet
     * @throws CardPacketException
     */
    protected void thereIsNoCardLeftInThePacket() throws CardPacketException {
        // It throws an exception now but I think that, in the future, it could be a good idea to refill the packet. But I'am not sure that it's one of the rules.
        throw new CardPacketException("Empty packet");
    }


    /**
     * Get the card in the top of the packet
     * @return the card in the top of the packet
     * @throws CardPacketException
     */
    public Card popCard() throws CardPacketException {
        if (! cards.hasNext())
            this.thereIsNoCardLeftInThePacket();
        return cards.next();
    }


}
