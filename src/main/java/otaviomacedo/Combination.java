package otaviomacedo;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.Set;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Multimaps.filterKeys;
import static com.google.common.collect.Multimaps.index;
import static otaviomacedo.Rank.appearsNTimesInHand;

public enum Combination implements Predicate<Deck> {

    /**
     * A straight flush is a hand that contains five cards in sequence, all of the same
     * suit, such as Q♣ J♣ 10♣ 9♣ 8♣(a hand that meets the requirement of both a straight
     * and a flush).
     */
    STRAIGHT_FLUSH(9) {
        @Override
        protected boolean validate(Iterable<Card> hand) {
            return thereIsOnlyOneSuit(hand) && (cardsAreInSequence(hand) || cardsAreInAlternativeSequence(hand));
        }
    },

    /**
     * Four of a kind, also known as quads or poker, is a poker hand such as 9♣ 9♠ 9♦ 9♥
     * J♥, that contains all four cards of one rank and any other (unmatched) card.
     */
    FOUR_OF_A_KIND(8) {
        @Override
        protected boolean validate(final Iterable<Card> hand) {
            return atLeastOneOfEach(ranks(hand), appearsNTimesInHand(4, hand));
        }

        private boolean atLeastOneOfEach(Multimap<Rank, Card> ranks, Predicate<Rank> rankPredicate) {
            return !filterKeys(ranks, rankPredicate).isEmpty();
        }

        private Multimap<Rank, Card> ranks(Iterable<Card> hand) {
            return index(hand, Card.TO_RANK);
        }
    },

    /**
     * A full house, also known as a full boat, is a hand such as 3♣ 3♠ 3♦ 6♣ 6♥, that
     * contains three matching cards of one rank and two matching cards of another rank.
     */
    FULL_HOUSE(7) {
        @Override
        protected boolean validate(Iterable<Card> hand) {
            return countingRule(hand, 2, 3);
        }
    },

    /**
     * A flush is a poker hand such as Q♣ 10♣ 7♣ 6♣ 4♣, where all five cards are of the
     * same suit, but not in sequence.
     */
    FLUSH(6) {
        @Override
        protected boolean validate(Iterable<Card> hand) {
            return thereIsOnlyOneSuit(hand) && !cardsAreInSequence(hand);
        }
    },

    /**
     * A straight is a poker hand such as Q♣ J♠ 10♠ 9♥ 8♥, that contains five cards of
     * sequential rank in at least two different suits.
     */
    STRAIGHT(5) {
        @Override
        protected boolean validate(Iterable<Card> hand) {
            return (cardsAreInSequence(hand) || cardsAreInAlternativeSequence(hand)) && thereAreAtLeastNSuits(hand, 2);
        }
    },

    /**
     * Three of a kind, also called trips or a set, is a poker hand such as 2♦ 2♠ 2♣ K♠ 6♥
     * that contains three cards of the same rank, plus two cards which are not of this
     * rank nor the same as each other.
     */
    THREE_OF_A_KIND(4) {
        @Override
        protected boolean validate(Iterable<Card> hand) {
            return countingRule(hand, 3, 1, 1);
        }
    },

    /**
     * A poker hand such as J♥ J♣ 4♣ 4♠ 9♥, that contains two cards of the same rank, plus
     * two cards of another rank (that match each other but not the first pair), plus any
     * card not of either rank, is called two pair.
     */
    TWO_PAIRS(3) {
        @Override
        protected boolean validate(Iterable<Card> hand) {
            return countingRule(hand, 2, 2, 1);
        }
    },

    /**
     * One pair is a poker hand such as 4♥ 4♠ K♠ 10♦ 5♠, that contains two cards of one
     * rank, plus three cards which are not of this rank nor the same as each other.
     */
    ONE_PAIR(2) {
        @Override
        protected boolean validate(Iterable<Card> hand) {
            return countingRule(hand, 2, 1, 1, 1);
        }
    },

    /**
     * A high-card or no-pair hand is a poker hand such as K♥ J♥ 8♣ 7♦ 4♠, made of any
     * five cards not meeting any of the above requirements.
     */
    HIGHEST_CARD(1) {
        @Override
        protected boolean validate(Iterable<Card> hand) {
            return true;
        }
    };

    private static boolean countingRule(Iterable<Card> hand, Integer... expected) {
        return counts(index(hand, Card.TO_RANK)).equals(ImmutableMultiset.copyOf(expected));
    }

    public static Iterable<Combination> inDescendingOrder() {
        return Ordering.natural().reverse().onResultOf(new Function<Combination, Integer>() {
            @Override
            public Integer apply(Combination combination) {
                return combination.getValue();
            }
        }).sortedCopy(newArrayList(Combination.values()));
    }

    private static boolean cardsAreInSequence(Iterable<Card> hand) {
        return Card.areInSequence(ImmutableList.copyOf(hand));
    }

    private static boolean cardsAreInAlternativeSequence(Iterable<Card> hand) {
        return Card.areInAlternativeSequence(ImmutableList.copyOf(hand));
    }

    private int value;

    private Combination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static <K> Multiset<Integer> counts(Multimap<K, ?> index) {
        Multiset<Integer> result = HashMultiset.create();
        for (K key : index.keySet()) {
            result.add(index.get(key).size());
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

    @Override
    public boolean apply(Deck deck) {
        Set<Iterable<Card>> hands = deck.getAllHands();
        for (Iterable<Card> hand : hands) {
            if (validate(hand)) {
                return true;
            }
        }
        return false;
    }

    protected abstract boolean validate(Iterable<Card> hand);

    @Override
    public String toString() {
        return this.name().toLowerCase().replace("_", "-");
    }
}