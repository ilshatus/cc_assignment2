package tokens;

import java.util.Arrays;
import java.util.HashSet;

public class DelimiterToken extends Token {
    private static final String[] delimiters = { ".", ";", ",", "\n"};
    private static final HashSet<String> delimitersHashSet = new HashSet<>(Arrays.asList(delimiters));

    public static boolean isDelimeter(String lexeme) {
        if (lexeme == null) return false;
        return delimitersHashSet.contains(lexeme);
    }

    public static DelimiterToken getToken(String lexeme) {
        if (isDelimeter(lexeme)) {
            return new DelimiterToken(lexeme);
        }
        return null;
    }

    private String lexeme;

    private DelimiterToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_delimiter(%s)", lexeme);
    }
}
