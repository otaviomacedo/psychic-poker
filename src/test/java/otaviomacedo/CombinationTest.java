package otaviomacedo;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static otaviomacedo.Combination.*;

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

    @Test
    public void validFullHouse() throws Exception {
        combinationAppliesToCards(
                FULL_HOUSE,
                "2H", "2S", "3H", "3S", "3C", "2D", "9C", "3D", "6C", "TH");
    }

    @Test
    public void invalidFullHouse() throws Exception {
        combinationDoesNotApplyToCards(
                FULL_HOUSE,
                "2H", "4D", "2S", "3S", "9D", "4C", "9C", "TC", "TH", "3H");
    }

    @Test
    public void validFlush() throws Exception {
        combinationAppliesToCards(
                FLUSH,
                "2H", "AD", "5H", "AC", "7H", "AH", "6H", "9H", "4H", "3C");
    }

    @Test
    public void invalidFlush() throws Exception {
        combinationDoesNotApplyToCards(
                FLUSH,
                "3H", "AD", "5H", "AC", "7H", "AS", "6H", "9C", "4H", "3C");
    }

    @Test
    public void validStraight() throws Exception {
        combinationAppliesToCards(
                STRAIGHT,
                "AC", "2D", "9C", "3S", "KD", "5S", "4D", "KS", "AS", "4C");
    }

    @Test
    public void invalidStraight() throws Exception {
        combinationDoesNotApplyToCards(
                STRAIGHT,
                "6S", "2S", "9C", "3S", "KD", "5S", "TH", "TD", "AD", "4S");
    }

    //    'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'
    @Test
    public void validThreeOfAKind() throws Exception {
        combinationAppliesToCards(
                THREE_OF_A_KIND,
                "KS", "AH", "2H", "3C", "4H", "KC", "2C", "TC", "2D", "AS");
    }

    @Test
    public void invalidThreeOfAKind() throws Exception {
        combinationDoesNotApplyToCards(
                THREE_OF_A_KIND,
                "AS", "8C", "KH", "3S", "QH", "TH", "JC", "7C", "4C", "2D");
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
