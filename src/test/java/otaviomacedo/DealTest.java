package otaviomacedo;

import org.junit.*;

import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static otaviomacedo.Combination.TWO_PAIRS;

public class DealTest {

    @Test
    public void test() throws Exception{
        Deal deal = new Deal(new Deck(cards("AH", "2C", "9S", "AD", "3C", "QH", "KS", "JS", "JD", "KD")));
        assertThat(deal.bestCombination(), is(TWO_PAIRS));
    }

    private static List<Card> cards(String...codes) {
        List<Card> cards = newArrayListWithExpectedSize(codes.length);
        for (String code : codes) {
            cards.add(new Card(code));
        }
        return cards;
    }

}
