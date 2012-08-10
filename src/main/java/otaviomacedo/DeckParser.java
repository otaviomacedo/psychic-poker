package otaviomacedo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Iterables.transform;

/**
 * Parses the input provided by a reader and transforms each line into a deck of cards.
 */
public class DeckParser implements Iterable<Deck> {

    private final BufferedReader reader;

    public DeckParser(Reader r) throws IOException {
        reader = new BufferedReader(r);
    }

    @Override
    public Iterator<Deck> iterator() {
        return new DeckIterator();
    }

    private class DeckIterator extends UnmodifiableIterator<Deck> {
        private String nextLine;

        private DeckIterator() {
            lookAhead();
        }

        @Override
        public boolean hasNext() {
            return nextLine != null;
        }

        @Override
        public Deck next() {
            String value = nextLine;
            lookAhead();
            List<String> codes = ImmutableList.copyOf(value.split(" "));
            return new Deck(transform(codes, Card.FROM_CODE));
        }

        private void lookAhead() {
            try {
                nextLine = reader.readLine();
            } catch (IOException e) {
                throw new IllegalStateException("The program could not read the input data", e);
            }
        }
    }
}