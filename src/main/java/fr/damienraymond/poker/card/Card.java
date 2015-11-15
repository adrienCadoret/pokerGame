package fr.damienraymond.poker.card;

/**
 * Created by damien on 02/10/2015.
 */
public class Card implements Comparable<Card> {

    private Color color;
    private Level level;

    public Card(Level level, Color color) {
        this.level = level;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return getColor() == card.getColor() && getLevel() == card.getLevel();

    }

    @Override
    public int hashCode() {
        int result = getColor().hashCode();
        result = 31 * result + getLevel().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return level + "-" + color;
    }

    @Override
    public int compareTo(Card o) {
        return this.level.compareTo(o.getLevel());
    }
}
