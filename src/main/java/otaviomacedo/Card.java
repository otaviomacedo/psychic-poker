package otaviomacedo;


import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Card implements Comparable<Card>{
    public static enum Suit{CLUBS, DIAMONDS, HEARTS, SPADES}

    public static Function<Card, Suit> TO_SUIT = new Function<Card, Suit>() {
        @Override
        public Suit apply(Card card) {
            return card.getSuit();
        }
    };

    public static Function<Card, Rank> TO_VALUE = new Function<Card, Rank>() {
        @Override
        public Rank apply(Card card) {
            return card.getRank();
        }
    };

    private static final List<String> SORTED_VALUES = Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
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

    public static boolean areInSequence(List<Card> cards){
        return Rank.inSequence(Lists.transform(cards, TO_VALUE));
    }
}
