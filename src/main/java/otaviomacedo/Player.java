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
            Deal deal = new Deal(deck);
            resultWriter.write(deck, deal.bestCombination());
        }
    }
}
