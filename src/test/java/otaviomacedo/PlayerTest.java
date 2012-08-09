package otaviomacedo;

import com.google.common.collect.ImmutableList;
import org.junit.*;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlayerTest {

    private static final String INPUT = "TH JH QC QD QS QH KH AH 2S 6S\n"+
                                        "2H 2S 3H 3S 3C 2D 3D 6C 9C TH\n"+
                                        "2H 2S 3H 3S 3C 2D 9C 3D 6C TH\n"+
                                        "2H AD 5H AC 7H AH 6H 9H 4H 3C\n"+
                                        "AC 2D 9C 3S KD 5S 4D KS AS 4C\n"+
                                        "KS AH 2H 3C 4H KC 2C TC 2D AS\n"+
                                        "AH 2C 9S AD 3C QH KS JS JD KD\n"+
                                        "6C 9C 8C 2D 7C 2H TC 4C 9S AH\n"+
                                        "3D 5S 2H QD TD 6S KH 9H AD QH\n";

    private final Player player = new Player(deckParser(), new StandardOutputWriter());

    @Test
    public void test() throws Exception{
        player.play();
    }


    @Test
    public void testname() throws Exception {
    }











    private DeckParser deckParser() {
        try {
            return new DeckParser(new StringReader(INPUT));
        } catch (IOException e) {
            //should never happen!
            return null;
        }
    }
}
