package otaviomacedo;

/**
 * Writes the result of some calculation involving a deck of cards and the corresponding combination.
 */
public interface ResultWriter {
    void write(Deck deck, Combination combination);
}
