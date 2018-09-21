package tokens.literals;

import tokens.Token;

import java.util.Arrays;
import java.util.HashSet;

public class LogicalLiteralToken extends Token {
    private static final String[] keywords = { "true", "false" };
    private static final HashSet<String> keywordsHashSet = new HashSet<>(Arrays.asList(keywords));

    public static boolean isLogicalLiteral(String lexeme) {
        if (lexeme == null) return false;
        return keywordsHashSet.contains(lexeme);
    }

    private String lexeme;

    private LogicalLiteralToken(String lexeme) {
        this.lexeme = lexeme;
    }

    public LogicalLiteralToken getToken(String lexeme) {
        if (isLogicalLiteral(lexeme))
            return new LogicalLiteralToken(lexeme);
        return null;
    }

    @Override
    public String toString() {
        return String.format("T_literal_logical(%s)", lexeme);
    }
}
