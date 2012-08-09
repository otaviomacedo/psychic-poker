package otaviomacedo;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

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

    private final PrintStream stream = mock(PrintStream.class);
    private final Player player = new Player(deckParser(), new PrintStreamResultWriter(stream));

    @Test
    public void endToEnd() throws Exception{
        player.play();

        verify(stream).println("Hand: TH JH QC QD QS Deck: QH KH AH 2S 6S Best hand: straight-flush");
        verify(stream).println("Hand: 2H 2S 3H 3S 3C Deck: 2D 3D 6C 9C TH Best hand: four-of-a-kind");
        verify(stream).println("Hand: 2H 2S 3H 3S 3C Deck: 2D 9C 3D 6C TH Best hand: full-house");
        verify(stream).println("Hand: 2H AD 5H AC 7H Deck: AH 6H 9H 4H 3C Best hand: flush");
        verify(stream).println("Hand: AC 2D 9C 3S KD Deck: 5S 4D KS AS 4C Best hand: straight");
        verify(stream).println("Hand: KS AH 2H 3C 4H Deck: KC 2C TC 2D AS Best hand: three-of-a-kind");
        verify(stream).println("Hand: AH 2C 9S AD 3C Deck: QH KS JS JD KD Best hand: two-pairs");
        verify(stream).println("Hand: 6C 9C 8C 2D 7C Deck: 2H TC 4C 9S AH Best hand: one-pair");
        verify(stream).println("Hand: 3D 5S 2H QD TD Deck: 6S KH 9H AD QH Best hand: highest-card");
    }


    private DeckParser deckParser() {
        try {
            return new DeckParser(new StringReader(INPUT));
        } catch (IOException e) {
            return null;
        }
    }
}
