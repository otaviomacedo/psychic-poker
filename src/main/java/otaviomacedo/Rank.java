package otaviomacedo;

import com.google.common.base.Objects;

import java.util.Arrays;
import java.util.List;

public class Rank implements Comparable<Rank>{

    private static final List<Character> SORTED_VALUES = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private static final List<Character> ALTERNATIVE_SORTED_VALUES = Arrays.asList('K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'A');
    private final char code;

    public Rank(char code) {
        this.code = code;
    }

    @Override
    public int compareTo(Rank o) {
        return this.index(SORTED_VALUES).compareTo(o.index(SORTED_VALUES));
    }

    public static boolean inSequence(List<Rank> ranks){
        for (int i = 1; i < ranks.size(); i++) {
            if (ranks.get(i).index(SORTED_VALUES) > ranks.get(i-1).index(SORTED_VALUES) + 1){
                return false;
            }
        }
        return true;
    }

    public static boolean inAlternativeSequence(List<Rank> ranks){
        for (int i = 1; i < ranks.size(); i++) {
            if (ranks.get(i).index(ALTERNATIVE_SORTED_VALUES) > ranks.get(i-1).index(ALTERNATIVE_SORTED_VALUES) + 1){
                return false;
            }
        }
        return true;
    }

    private Integer index(List<Character> reference){
        return reference.indexOf(this.code);
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
