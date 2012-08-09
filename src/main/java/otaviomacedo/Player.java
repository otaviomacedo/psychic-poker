package otaviomacedo;

public class Player {

    private final Iterable<Deck> deckSource;
    private final ResultWriter resultWriter;

    public Player(ResultWriter resultWriter, Iterable<Deck> deckSource) {
        this.resultWriter = resultWriter;
        this.deckSource = deckSource;
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
