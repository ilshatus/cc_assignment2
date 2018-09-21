package tokens.literals;

import tokens.Token;
import tokens.enums.State;
import tokens.builders.SimpleRegexBuilder;

import java.util.regex.Pattern;

public class MultilineStringLiteralToken extends Token {
    private String lexeme;

    private MultilineStringLiteralToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_literal_multiline_string(%s)", lexeme);
    }


    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp =
                "^\"\"\"([^\\\\\\r]?|\\\\(u(\\d|[a-f]|[A-F]){4}|[0-7]{3}|[btnfr\\\\'\"]))*\"\"\"$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp, Pattern.MULTILINE);
        }

        @Override
        public MultilineStringLiteralToken build() {
            if (state.equals(State.MATCH))
                return new MultilineStringLiteralToken(lexeme.toString());
            return null;
        }
    }
}
