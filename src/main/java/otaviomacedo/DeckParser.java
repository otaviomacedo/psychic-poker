package otaviomacedo;

import com.google.common.collect.ImmutableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

/**
 * Parses the input provided by a reader and transforms each line into a deck of cards.
 */
public class DeckParser implements Iterable<Deck> {

    //TODO This list grows exponentially. So it's a better idea to generate each deck lazily.
    private final List<Deck> decks = newArrayList();

    public DeckParser(Reader r) throws IOException {
        BufferedReader reader = new BufferedReader(r);
        String line;
        while ((line = reader.readLine()) != null){
            List<String> codes = ImmutableList.copyOf(line.split(" "));
            decks.add(new Deck(transform(codes, Card.FROM_CODE)));
        }
    }

    @Override
    public Iterator<Deck> iterator() {
        return decks.iterator();
    }
}
