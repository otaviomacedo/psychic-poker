package otaviomacedo;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.filter;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newHashSetWithExpectedSize;

public enum Combination implements Predicate<Deck> {

    STRAIGHT_FLUSH(9) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                if (thereIsOnlyOneSuit(hand) && (cardsAreInSequence(hand) || cardsAreInAlternativeSequence(hand))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "straight-flush";
        }
    },

    FOUR_OF_A_KIND(8) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                ImmutableListMultimap<Rank, Card> index = Multimaps.index(hand, Card.TO_RANK);
                for (Rank rank : index.keySet()) {
                    if (frequency(hand, rank) == 4) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "four-of-a-kind";
        }

        private int frequency(Iterable<Card> cards, Rank rank){
            return size(Iterables.filter(transform(cards, Card.TO_RANK), equalTo(rank)));
        }
    },

    /**
     * A full house, also known as a full boat, is a hand such as 3♣ 3♠ 3♦ 6♣ 6♥, that
     * contains three matching cards of one rank and two matching cards of another rank.
     */
    FULL_HOUSE(7) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                if (counts(Multimaps.index(hand, Card.TO_RANK)).equals(ImmutableMultiset.of(2, 3))){
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "full-house";
        }
    },

    /**
     * A flush is a poker hand such as Q♣ 10♣ 7♣ 6♣ 4♣, where all five cards are of the
     * same suit, but not in sequence.
     */
    FLUSH(6) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                if (thereIsOnlyOneSuit(hand) && !cardsAreInSequence(hand)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "flush";
        }
    },

    /**
     * A straight is a poker hand such as Q♣ J♠ 10♠ 9♥ 8♥, that contains five cards of
     * sequential rank in at least two different suits.
     */
    STRAIGHT(5) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                if ((cardsAreInSequence(hand) || cardsAreInAlternativeSequence(hand)) && thereAreAtLeastNSuits(hand, 2)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "straight";
        }
    },

    /**
     * Three of a kind, also called trips or a set, is a poker hand such as 2♦ 2♠ 2♣ K♠ 6♥
     * that contains three cards of the same rank, plus two cards which are not of this
     * rank nor the same as each other.
     */
    THREE_OF_A_KIND(4) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                if (suitCounts(Multimaps.index(hand, Card.TO_SUIT)).equals(ImmutableMultiset.of(3, 1, 1))){
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "three-of-a-kind";
        }
    },

    /**
     * A poker hand such as J♥ J♣ 4♣ 4♠ 9♥, that contains two cards of the same rank, plus
     * two cards of another rank (that match each other but not the first pair), plus any
     * card not of either rank, is called two pair.
     */
    TWO_PAIRS(3) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                if (suitCounts(Multimaps.index(hand, Card.TO_SUIT)).equals(ImmutableMultiset.of(2, 2, 1))){
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "two-pairs";
        }
    },

    /**
     * One pair is a poker hand such as 4♥ 4♠ K♠ 10♦ 5♠, that contains two cards of one
     * rank, plus three cards which are not of this rank nor the same as each other.
     */
    ONE_PAIR(2) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                if (suitCounts(Multimaps.index(hand, Card.TO_SUIT)).equals(ImmutableMultiset.of(2, 1, 1, 1))){
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "one-pair";
        }
    },

    /**
     * A high-card or no-pair hand is a poker hand such as K♥ J♥ 8♣ 7♦ 4♠, made of any
     * five cards not meeting any of the above requirements.
     */
    HIGHEST_CARD(1) {
        @Override
        public boolean apply(Deck deck) {
            return true;
        }

        @Override
        public String toString() {
            return "highest-card";
        }
    };

    public static Iterable<Combination> inDescendingOrder(){
        return Ordering.natural().reverse().onResultOf(new Function<Combination, Integer>() {
            @Override
            public Integer apply(Combination combination) {
                return combination.getValue();
            }
        }).sortedCopy(newArrayList(Combination.values()));
    }


    private static boolean cardsAreInSequence(Iterable<Card> hand) {
        return Card.areInSequence(Ordering.natural().sortedCopy(hand));
    }

    private static boolean cardsAreInAlternativeSequence(Iterable<Card> hand) {
        return Card.areInAlternativeSequence(Ordering.natural().sortedCopy(hand));
    }

    private int value;

    private Combination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static <K> Multiset<Integer> counts(Multimap<K, ?> index){
        Multiset<Integer> result = HashMultiset.create();
        for (K key : index.keySet()) {
            result.add(index.get(key).size());
        }
        return result;
    }

    private static Multiset<Integer> suitCounts(Multimap<Card.Suit, ?> index){
        Multiset<Integer> result = HashMultiset.create();
        for (Card.Suit suit : index.keySet()) {
            result.add(index.get(suit).size());
        }
        return result;
    }

    private static boolean thereIsOnlyOneSuit(Iterable<Card> hand) {
        return thereAreNSuits(hand, 1);
    }

    private static boolean thereAreNSuits(Iterable<Card> hand, int count) {
        return ImmutableSet.copyOf(transform(hand, Card.TO_SUIT)).size() == count;
    }

    private static boolean thereAreAtLeastNSuits(Iterable<Card> hand, int count) {
        return ImmutableSet.copyOf(transform(hand, Card.TO_SUIT)).size() >= count;
    }

    private static final List<List<Integer>> REPLACEMENT_LISTS = replacementLists();

    private static Set<Iterable<Card>> generateAllHands(Deck deck) {
        Set<Iterable<Card>> hands = newHashSetWithExpectedSize((int)Math.pow(2, size(deck)) - 1);
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

    private static List<List<Integer>> replacementLists(){
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
            for (int i = sublist.get(sublist.size()-1) + 1; i < 5; i++) {
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
