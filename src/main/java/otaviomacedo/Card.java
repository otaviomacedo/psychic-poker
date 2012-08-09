package otaviomacedo;


import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
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

    public static Function<Card, Value> TO_VALUE = new Function<Card, Value>() {
        @Override
        public Value apply(Card card) {
            return card.getValue();
        }
    };

    private static final List<String> SORTED_VALUES = Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");

    private final Suit suit;
    private final Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public int compareTo(Card o) {
        return this.getValue().compareTo(o.getValue());
    }

    public static boolean areInSequence(List<Card> cards){
        return Value.inSequence(Lists.transform(cards, TO_VALUE));
    }
}
