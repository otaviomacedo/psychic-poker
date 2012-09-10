package otaviomacedo;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import java.util.List;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Iterables.transform;
import static otaviomacedo.Card.TO_RANK;

public class Rank implements Comparable<Rank> {

    private static final List<Character> SORTED_VALUES = ImmutableList.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private static final List<Character> ALT_SORTED_VALUES = ImmutableList.of('K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'A');
    private final char code;

    public static Function<Rank, Character> TO_CODE = new Function<Rank, Character>() {
        @Override
        public Character apply(Rank rank) {
            return rank.code;
        }
    };

    public static Predicate<Rank> appearsNTimesInHand(final int frequency, final Iterable<Card> hand) {
        return new Predicate<Rank>() {
            @Override
            public boolean apply(Rank rank) {
                return frequency(hand, rank) == frequency;
            }
        };
    }

    private static int frequency(Iterable<Card> cards, Rank rank) {
        return size(filter(transform(cards, TO_RANK), equalTo(rank)));
    }

    public Rank(char code) {
        this.code = code;
    }

    @Override
    public int compareTo(Rank o) {
        return this.index(SORTED_VALUES).compareTo(o.index(SORTED_VALUES));
    }

    public static boolean inSequence(List<Rank> ranks) {
        return inSeq(ranks, SORTED_VALUES);
    }

    public static boolean inAlternativeSequence(List<Rank> ranks) {
        return inSeq(ranks, ALT_SORTED_VALUES);
    }

    private static boolean inSeq(List<Rank> ranks, List<Character> reference) {
        List<Rank> sorted = Ordering
                .explicit(reference)
                .onResultOf(Rank.TO_CODE)
                .sortedCopy(ranks);

        for (int i = 1; i < sorted.size(); i++) {
            if (sorted.get(i).index(reference) != sorted.get(i - 1).index(reference) + 1) {
                return false;
            }
        }
        return true;
    }

    private Integer index(List<Character> reference) {
        return reference.indexOf(this.code);
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Rank && equal(code, ((Rank) obj).code);
    }
}
