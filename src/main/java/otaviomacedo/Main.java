package otaviomacedo;

import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);

        try {
            player(reader).play();
        } catch (Exception e) {
            System.out.println("Could not process input. " + e.getMessage());
        } finally {
            reader.close();
        }
    }

    private static Player player(InputStreamReader reader) throws IOException {
        return new Player(new DeckParser(reader), new PrintStreamResultWriter(System.out));
    }
}