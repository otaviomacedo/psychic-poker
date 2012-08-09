package otaviomacedo;

public class Player {

    private final Iterable<Deck> deckSource;
    private final ResultWriter resultWriter;

    public Player(Iterable<Deck> deckSource, ResultWriter resultWriter) {
        this.deckSource = deckSource;
        this.resultWriter = resultWriter;
    }

    public void play(){
        for (Deck deck : deckSource) {
            resultWriter.write(deck, bestCombination(deck));
        }
    }

    private Combination bestCombination(Deck deck) {
        return new Deal(deck).bestCombination();
    }
}
