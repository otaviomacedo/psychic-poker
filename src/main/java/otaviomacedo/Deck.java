package otaviomacedo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSetWithExpectedSize;

/**
 * A deck of cards, including all the cards in the player's hand and the top cards on the table.
 */
public class Deck implements Iterable<Card> {

    private final Iterable<Card> cards;
    private final Set<Iterable<Card>> allHands;

    public Deck(Iterable<Card> cards) {
        this.cards = cards;
        allHands = generateAllHands(cards);
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

    private static final List<List<Integer>> REPLACEMENT_LISTS = replacementLists();

    private static Set<Iterable<Card>> generateAllHands(Iterable<Card> deck) {
        Set<Iterable<Card>> hands = newHashSetWithExpectedSize((int) Math.pow(2, size(deck)) - 1);
        List<Card> originalHand = ImmutableList.copyOf(Iterables.limit(deck, 5));
        List<Card> availableCards = ImmutableList.copyOf(Iterables.skip(deck, 5));

        for (List<Integer> points : REPLACEMENT_LISTS) {
            List<Card> foo = newArrayList(originalHand);
            for (int i = 0; i < points.size(); i++) {
                foo.set(points.get(i), availableCards.get(i));
            }
            hands.add(foo);
        }

        return hands;
    }

    private static List<List<Integer>> replacementLists() {
        List<List<Integer>> list = Lists.<List<Integer>>newArrayList(
                newArrayList(0),
                newArrayList(1),
                newArrayList(2),
                newArrayList(3),
                newArrayList(4));

        for (int i = 1; i < 5; i++) {
            list.addAll(derive(list));
        }

        return list;
    }

    private static List<List<Integer>> derive(List<List<Integer>> list) {
        List<List<Integer>> result = newArrayList();
        for (List<Integer> sublist : list) {
            for (int i = sublist.get(sublist.size() - 1) + 1; i < 5; i++) {
                result.add(cons(sublist, i));
            }
        }
        return result;
    }

    private static List<Integer> cons(List<Integer> sublist, int i) {
        List<Integer> temp = newArrayList(sublist);
        temp.add(i);
        return temp;
    }
}
