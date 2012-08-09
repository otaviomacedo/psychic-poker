package otaviomacedo;

import com.google.common.base.Objects;

import java.util.Arrays;
import java.util.List;

public class Rank implements Comparable<Rank>{

    private static final List<Character> SORTED_VALUES = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private final char code;

    public Rank(char code) {
        this.code = code;
    }

    @Override
    public int compareTo(Rank o) {
        return this.index().compareTo(o.index());
    }

    public static boolean inSequence(List<Rank> ranks){
        int sum = 0;
        for (Rank rank : ranks) {
            sum += rank.index();
        }

        return sum == ((ranks.get(0).index() + ranks.get(ranks.size() - 1).index()) * ranks.size()) / 2;
    }

    private Integer index(){
        return SORTED_VALUES.indexOf(this.code);
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rank) {
            final Rank other = (Rank) obj;
            return Objects.equal(code, other.code);
        } else {
            return false;
        }
    }
}