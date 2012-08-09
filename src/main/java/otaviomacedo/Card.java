package otaviomacedo;


import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;

import java.util.List;

public class Card implements Comparable<Card> {
    public static enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        private static BiMap<Character, Suit> CODES = ImmutableBiMap.of(
                'C', CLUBS,
                'D', DIAMONDS,
                'H', HEARTS,
                'S', SPADES);

        public static Suit forCode(char c) {
            return CODES.get(c);
        }

        @Override
        public String toString() {
            return String.valueOf(CODES.inverse().get(this));
        }
    }

    public static Function<Card, Suit> TO_SUIT = new Function<Card, Suit>() {
        @Override
        public Suit apply(Card card) {
            return card.getSuit();
        }
    };

    public static Function<Card, Rank> TO_RANK = new Function<Card, Rank>() {
        @Override
        public Rank apply(Card card) {
            return card.getRank();
        }
    };

    public static Function<String, Card> FROM_CODE = new Function<String, Card>() {
        @Override
        public Card apply(String code) {
            return new Card(code);
        }
    };

    private final Suit suit;
    private final Rank rank;

    public Card(String code) {
        rank = new Rank(code.charAt(0));
        suit = Suit.forCode(code.charAt(1));
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public int compareTo(Card o) {
        return this.getRank().compareTo(o.getRank());
    }

    public static boolean areInSequence(List<Card> cards) {
        return Rank.inSequence(Lists.transform(cards, TO_RANK));
    }

    public static boolean areInAlternativeSequence(List<Card> cards) {
        return Rank.inAlternativeSequence(Lists.transform(cards, TO_RANK));
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
}
