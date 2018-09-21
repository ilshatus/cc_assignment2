package tokens;

import java.util.*;

public class KeywordToken extends Token {
    private static final String[] keywords = { "abstract", "do", "finally", "import", "null", "protected",
            "throw", "val", "case", "else", "for", "lazy", "object", "return", "trait", "var", "catch",
            "extends", "forSome", "macro", "override", "sealed", "try", "while", "class", "if",
            "match", "package", "super", "with", "def", "final", "implicit", "new", "private",
            "this", "type", "yield", "_", ":", "=", "=>", "<-", "<:", ">:", "#", "@" };

    private static final HashSet<String> keywordsHashSet = new HashSet<>(Arrays.asList(keywords));

    private static String mapUnicodeKeywords(String lexeme) {
        if (lexeme.equals("←"))
            return "<-";
        if (lexeme.equals("⇒"))
            return "=>";
        return lexeme;
    }

    public static boolean isKeyword(String lexeme) {
        if (lexeme == null) return false;
        return keywordsHashSet.contains(mapUnicodeKeywords(lexeme));
    }

    private String lexeme;

    private KeywordToken(String lexeme) {
        this.lexeme = mapUnicodeKeywords(lexeme);
    }

    public static KeywordToken getToken(String lexeme) {
        if (isKeyword(lexeme))
            return new KeywordToken(lexeme);
        return null;
    }

    @Override
    public String toString() {
        return String.format("T_keyword(%s)", lexeme);
    }
}
