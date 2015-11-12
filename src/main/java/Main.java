import fr.damienraymond.poker.PokerException;
import fr.damienraymond.poker.PokerSimple;
import fr.damienraymond.poker.utils.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Logger.showLoggerStatut();
        PokerSimple pokerSimple = new PokerSimple();
        try {
            pokerSimple.poker(Arrays.asList("Damien, Clément"));
        } catch (PokerException e) {
            Logger.error("An error occurred in game", e);
        }
    }
}
