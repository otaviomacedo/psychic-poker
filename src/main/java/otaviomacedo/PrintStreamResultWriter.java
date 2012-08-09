package otaviomacedo;

import com.google.common.base.Joiner;

import java.io.PrintStream;
import java.text.MessageFormat;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Iterables.skip;

/**
 * Writes the results to a {@link PrintStream}. The results are formatted according to the problem specification.
 */
public class PrintStreamResultWriter implements ResultWriter {

    private final PrintStream stream;

    public PrintStreamResultWriter(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void write(Deck deck, Combination combination) {
        Joiner joiner = Joiner.on(" ");
        stream.println(MessageFormat.format("Hand: {0} Deck: {1} Best hand: {2}",
                joiner.join(limit(deck, 5)),
                joiner.join(skip(deck, 5)),
                combination));
    }
}
