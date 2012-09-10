package otaviomacedo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSetWithExpectedSize;

/**
 * A deck of cards, including all the cards in the player's hand and the top cards on the table.
 */
public class Deck implements Iterable<Card> {

    public static final int HAND_SIZE = 5;
    private final Iterable<Card> cards;
    private final Set<Iterable<Card>> allHands;

    public Deck(Iterable<Card> cards) {
        this.cards = cards;
        allHands = generateAllHands(copyOf(cards));
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    /**
     * Calculates all possible hands that can be generated from this deck.
     *
     * @return A set containing 2^n - 1 hands, where n is the number of cards this deck contains.
     */
    public Set<Iterable<Card>> getAllHands() {
        return allHands;
    }

    private static Set<Iterable<Card>> generateAllHands(List<Card> deck) {
        Set<Iterable<Card>> hands = newHashSetWithExpectedSize((int) Math.pow(2, size(deck)) - 1);

        for (byte mask = 1; mask <= (1 << HAND_SIZE); mask++) {
            hands.add(generateHands(mask, deck));
        }

        return hands;
    }

    private static List<Card> generateHands(byte mask, List<Card> deck) {
        return replace(deck.subList(0, HAND_SIZE), deck.subList(HAND_SIZE, deck.size()), mask);
    }

    private static List<Card> replace(List<Card> hand, List<Card> available, byte mask){
        List<Card> replaced = newArrayList(hand);

        int i = 0, j = 0;

        for (byte ref = 1 << HAND_SIZE - 1; ref > 0; ref >>= 1, j++){
            if ((ref & mask) != 0){
                replaced.set(j, available.get(i++));
            }
        }

        return replaced;
    }
}