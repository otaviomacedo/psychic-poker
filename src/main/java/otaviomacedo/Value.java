package otaviomacedo;

import java.util.Arrays;
import java.util.List;

public class Value implements Comparable<Value>{

    private static final List<Character> SORTED_VALUES = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private final char code;

    public Value(char code) {
        this.code = code;
    }

    @Override
    public int compareTo(Value o) {
        return this.index().compareTo(o.index());
    }

    public static boolean inSequence(List<Value> values){
        int sum = 0;
        for (Value value : values) {
            sum += value.index();
        }

        return sum == ((values.get(0).index() + values.get(values.size() - 1).index()) * values.size()) / 2;
    }

    private Integer index(){
        return SORTED_VALUES.indexOf(this.code);
    }
}
