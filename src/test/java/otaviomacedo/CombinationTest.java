package otaviomacedo;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static otaviomacedo.Combination.STRAIGHT_FLUSH;

public class CombinationTest {

//    TH JH QC QD QS QH KH AH 2S 6S

//    'A', 'K', 'Q', 'J', 'T'

    @Test
    public void validStraightFlush() throws Exception {
        combinationAppliesToCards(
                STRAIGHT_FLUSH,
                new Card("AH"), new Card("KH"), new Card("QH"), new Card("JH"), new Card("TH"));
    }

    @Test
    public void invalidStraightFlushWhenNotInSequence() throws Exception {
        combinationDoesNotApplyToCards(
                STRAIGHT_FLUSH,
                new Card("AH"), new Card("JH"), new Card("TH"), new Card("KH"), new Card("QH"));
    }

    @Test
    public void invalidStraightFlushWhenMoreThanOneSuit() throws Exception {
        combinationDoesNotApplyToCards(
                STRAIGHT_FLUSH,
                new Card("AH"), new Card("JD"), new Card("TH"), new Card("KH"), new Card("QH"));
    }


    private static void combinationAppliesToCards(Combination combination, Card...cards){
        assertTrue(combination.apply(new Deck(Arrays.asList(cards))));
    }

    private static void combinationDoesNotApplyToCards(Combination combination, Card...cards){
        assertFalse(combination.apply(new Deck(Arrays.asList(cards))));
    }

}
