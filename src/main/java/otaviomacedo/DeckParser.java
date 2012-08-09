package otaviomacedo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

public class DeckParser implements Iterable<Deck> {

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
