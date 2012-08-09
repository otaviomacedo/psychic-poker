package otaviomacedo;

import com.google.common.base.Predicate;
import com.google.common.collect.*;

import javax.annotation.Nullable;
import java.util.*;

public enum Combination implements Predicate<Deck> {

//Individual cards are ranked A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2.

//9    Hand: TH JH QC QD QS Deck: QH KH AH 2S 6S Best hand: straight-flush
//8    Hand: 2H 2S 3H 3S 3C Deck: 2D 3D 6C 9C TH Best hand: four-of-a-kind
//7    Hand: 2H 2S 3H 3S 3C Deck: 2D 9C 3D 6C TH Best hand: full-house
//6    Hand: 2H AD 5H AC 7H Deck: AH 6H 9H 4H 3C Best hand: flush
//5    Hand: AC 2D 9C 3S KD Deck: 5S 4D KS AS 4C Best hand: straight
//4    Hand: KS AH 2H 3C 4H Deck: KC 2C TC 2D AS Best hand: three-of-a-kind
//3    Hand: AH 2C 9S AD 3C Deck: QH KS JS JD KD Best hand: two-pairs
//2    Hand: 6C 9C 8C 2D 7C Deck: 2H TC 4C 9S AH Best hand: one-pair
//1    Hand: 3D 5S 2H QD TD Deck: 6S KH 9H AD QH Best hand: highest-card


    STRAIGHT_FLUSH(9){
        @Override public boolean apply(Deck deck) {
            Multimap<Card.Suit,Card> index = Multimaps.index(deck, Card.TO_SUIT);
            for (Card.Suit suit : index.keySet()) {
                List<Card> cards = Ordering.natural().sortedCopy(index.get(suit));
                if (cards.size() == 5 && Card.areInSequence(cards)){
                    return true;
                }
            }
            return false;
        }
    },

    FOUR_OF_A_KIND(8){
        @Override public boolean apply(Deck deck) {
            Multimap<Card.Suit,Card> index = Multimaps.index(deck, Card.TO_SUIT);
            for (Card.Suit suit : index.keySet()) {
                List<Card> cards = Ordering.natural().sortedCopy(index.get(suit));
                if (cards.size() == 4){
                    return true;
                }
            }
            return false;
        }
    },




    ;



    private int value;

    private Combination(int value) {
        this.value = value;
    }

    private static Set<String> cardSet(String...cards){
        return new HashSet<String>(Arrays.asList(cards));
    }

}
