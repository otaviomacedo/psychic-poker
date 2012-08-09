package otaviomacedo;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public enum Combination implements Predicate<Deck> {

    STRAIGHT_FLUSH(9) {
        @Override
        public boolean apply(Deck deck) {
            Set<Iterable<Card>> hands = generateAllHands(deck);
            for (Iterable<Card> hand : hands) {
                if (thereIsOnlyOneSuit(hand) && cardsAreInSequence(hand)) {
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
                for (Card.Suit suit : Card.Suit.values()) {
                    if (Iterables.frequency(hand, suit) == 4) {
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
                if (suitCounts(Multimaps.index(hand, Card.TO_SUIT)).equals(ImmutableMultiset.of(2, 3))){
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
                if (cardsAreInSequence(hand) && thereAreAtLeastNSuits(hand, 2)) {
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

    private static Predicate<Iterable<Card>> hasSize(final int count) {
        return new Predicate<Iterable<Card>>() {
            @Override
            public boolean apply(Iterable<Card> cards) {
                return Iterables.size(cards) == count;
            }
        };
    }

    private int value;

    private Combination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
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
        return ImmutableSet.copyOf(Iterables.transform(hand, Card.TO_SUIT)).size() == count;
    }

    private static boolean thereAreAtLeastNSuits(Iterable<Card> hand, int count) {
        return ImmutableSet.copyOf(Iterables.transform(hand, Card.TO_SUIT)).size() >= count;
    }

    private static Set<Iterable<Card>> generateAllHands(Deck deck) {
        ImmutableList<Card> cards = ImmutableList.copyOf(deck);
        Set<Iterable<Card>> hands = newHashSet();
        int start = 0, end = 5;

        while (end <= cards.size()) {
            hands.add(cards.subList(start++, end++));
        }
        return hands;
    }

}
