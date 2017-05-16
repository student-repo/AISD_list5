import java.util.Random;

public class RandomString {

    private static char[] symbols;

    private char firstLetter;
    private char lastLetter;


    private final Random random = new Random();

    private final char[] buf;

    public RandomString(int length, char firstLetter, char lastLetter) {
        if (length < 1)
            throw new IllegalArgumentException("length < 1: " + length);
        buf = new char[length];
        this.firstLetter = firstLetter;
        this.lastLetter = lastLetter;
    }

    public String nextString() {
        StringBuilder tmp = new StringBuilder();
        for (char ch = firstLetter; ch <= lastLetter; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();

        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}