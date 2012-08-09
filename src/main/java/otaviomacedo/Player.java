package otaviomacedo;

import com.google.common.collect.Iterables;

public class Player {

    private final Iterable<Deck> deckSource;
    private final ResultWriter resultWriter;

    /**
     * The player, who, for each input deck, decides what is the best combination and sends the results to be written.
     * @param deckSource the source of decks to be processed.
     * @param resultWriter receives the results calculated by the player.
     */
    public Player(Iterable<Deck> deckSource, ResultWriter resultWriter) {
        this.deckSource = deckSource;
        this.resultWriter = resultWriter;
    }

    public void play() {
        for (Deck deck : deckSource) {
            resultWriter.write(deck, bestCombination(deck));
        }
    }

    private Combination bestCombination(Deck deck) {
        Iterable<Combination> combinations = Combination.inDescendingOrder();
        for (Combination combination : combinations) {
            if (combination.apply(deck)) {
                return combination;
            }
        }

        //Just to return something meaningful and safe. But the method should never reach this point.
        return Iterables.getLast(combinations);
    }
}
