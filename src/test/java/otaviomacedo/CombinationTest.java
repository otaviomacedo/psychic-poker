package otaviomacedo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static otaviomacedo.Combination.FOUR_OF_A_KIND;
import static otaviomacedo.Combination.STRAIGHT_FLUSH;

public class CombinationTest {

    @Test
    public void validStraightFlush() throws Exception {
        combinationAppliesToCards(
                STRAIGHT_FLUSH,
                "TH", "JH", "QC", "QD", "QS", "QH", "KH", "AH", "2S", "6S");
    }

    @Test
    public void invalidStraightFlush() throws Exception {
        combinationDoesNotApplyToCards(
                STRAIGHT_FLUSH,
                "TS", "JH", "QC", "QD", "QS", "QH", "KH", "AH", "2S", "6S");
    }

    @Test
    public void validFourOfAKind() throws Exception {
        combinationAppliesToCards(
                FOUR_OF_A_KIND,
                "2H", "2S", "3H", "3S", "3C", "2D", "3D", "6C", "9C", "TH");
    }

    @Test
    public void invalidFourOfAKind() throws Exception {
        combinationDoesNotApplyToCards(
                FOUR_OF_A_KIND,
                "2H", "2S", "3H", "3S", "AC", "2D", "3D", "6C", "9C", "TH");
    }

    private static void combinationAppliesToCards(Combination combination, String... codes) {
        combinationAppliesToCards(combination, cards(codes), true);
    }

    private static void combinationDoesNotApplyToCards(Combination combination, String... codes) {
        combinationAppliesToCards(combination, cards(codes), false);
    }

    private static List<Card> cards(String[] codes) {
        List<Card> cards = newArrayListWithExpectedSize(codes.length);
        for (String code : codes) {
            cards.add(new Card(code));
        }
        return cards;
    }

    private static void combinationAppliesToCards(Combination combination, List<Card> cards, boolean applies) {
        assertThat(combination.apply(new Deck(cards)), is(applies));
    }
}
