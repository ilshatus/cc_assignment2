package tokens;

import tokens.enums.State;
import tokens.builders.SimpleRegexBuilder;

import java.util.regex.Pattern;

public class BackQuoteIdentifierToken extends Token {
    private String lexeme;

    private BackQuoteIdentifierToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_identifier(%s)", lexeme);
    }

    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp = "^`.*`$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp, Pattern.MULTILINE);
        }

        @Override
        public BackQuoteIdentifierToken build() {
            if (state.equals(State.MATCH))
                return new BackQuoteIdentifierToken(lexeme.toString());
            return null;
        }
    }
}
