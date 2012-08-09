package otaviomacedo;

import com.google.common.collect.Iterables;

public class Deal {
    private final Deck deck;

    public Deal(Deck deck) {
        this.deck = deck;
    }

    public Combination bestCombination() {
        Iterable<Combination> combinations = Combination.inDescendingOrder();
        for (Combination combination : combinations) {
            if (combination.apply(deck)){
                return combination;
            }
        }

        //Just to return something meaningful and safe. But the method should never reach this point.
        return Iterables.getLast(combinations);
    }
}
