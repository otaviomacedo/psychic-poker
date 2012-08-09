package otaviomacedo;

import java.util.Iterator;

public class Deck implements Iterable<Card>{

    private final Iterable<Card> cards;

    public Deck(Iterable<Card> cards) {
        this.cards = cards;
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }
}
