import fr.damienraymond.poker.PokerException;
import fr.damienraymond.poker.PokerSimple;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        PokerSimple pokerSimple = new PokerSimple();
        try {
            pokerSimple.poker(Arrays.asList("Damien, Clément"));
        } catch (PokerException e) {
            e.printStackTrace();
        }
    }
}
