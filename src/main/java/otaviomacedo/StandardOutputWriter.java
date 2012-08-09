package otaviomacedo;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import java.text.MessageFormat;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Iterables.skip;

public class StandardOutputWriter implements ResultWriter {

    @Override
    public void write(Deck deck, Combination combination) {
        Joiner joiner = Joiner.on(" ");
        System.out.println(MessageFormat.format("Hand: {0} Deck: {1} Best Hand: {2}",
                joiner.join(limit(deck, 5)),
                joiner.join(skip(deck, 5)),
                combination));
    }
}
