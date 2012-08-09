package otaviomacedo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Foo");
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (r.ready()) {
            String s = r.readLine();
            System.out.println(s + " => " + s.length());
        }
        r.close();

    }
}
