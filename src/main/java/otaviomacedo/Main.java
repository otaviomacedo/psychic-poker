package otaviomacedo;

import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);

        try {
            Player player = new Player(new DeckParser(reader), new StandardOutputWriter());
            player.play();
        } catch (Exception e) {
            System.out.println("Could not process input. " + e.getMessage());
        } finally {
            reader.close();
        }
    }
}