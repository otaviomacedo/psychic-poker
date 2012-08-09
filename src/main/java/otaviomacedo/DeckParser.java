package otaviomacedo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public class DeckParser implements Iterable<Deck> {


    public DeckParser(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();

        }
        reader.close();
    }

    @Override
    public Iterator<Deck> iterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
