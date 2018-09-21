package tokens;

import tokens.builders.SimpleRegexBuilder;
import tokens.enums.State;
import java.util.regex.Pattern;

public class KeywordToken extends Token {
    private static String mapUnicodeKeywords(String lexeme) {
        if (lexeme.equals("←"))
            return "<-";
        if (lexeme.equals("⇒"))
            return "=>";
        return lexeme;
    }

    private String lexeme;

    private KeywordToken(String lexeme) {
        this.lexeme = mapUnicodeKeywords(lexeme);
    }

    @Override
    public String toString() {
        return String.format("T_keyword(%s)", lexeme);
    }

    public static class Builder extends SimpleRegexBuilder {
        private static final String[] keywords = { "abstract", "do", "finally", "import", "null", "protected",
                "throw", "val", "case", "else", "for", "lazy", "object", "return", "trait", "var", "catch",
                "extends", "forSome", "macro", "override", "sealed", "try", "while", "class", "if",
                "match", "package", "super", "with", "def", "final", "implicit", "new", "private",
                "this", "type", "yield", "_", ":", "=", "=>", "<-", "<:", ">:", "#", "@" };

        public Builder() {
            super();

            StringBuilder regexp = new StringBuilder("^");

            for (String keyword : keywords) {
                regexp.append(keyword);
                regexp.append('|');
            }
            regexp.deleteCharAt(regexp.length() - 1);
            regexp.append('$');

            super.p = Pattern.compile(regexp.toString());
        }

        @Override
        public KeywordToken build() {
            if (state.equals(State.MATCH))
                return new KeywordToken(lexeme.toString());
            return null;
        }
    }
}
