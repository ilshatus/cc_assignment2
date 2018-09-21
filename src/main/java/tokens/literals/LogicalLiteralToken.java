package tokens.literals;

import tokens.Token;
import tokens.builders.SimpleRegexBuilder;
import tokens.enums.State;

import java.util.regex.Pattern;

public class LogicalLiteralToken extends Token {
    private String lexeme;

    private LogicalLiteralToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_literal_logical(%s)", lexeme);
    }


    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp =
                "^true|false$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp, Pattern.MULTILINE);
        }

        @Override
        public LogicalLiteralToken build() {
            if (state.equals(State.MATCH))
                return new LogicalLiteralToken(lexeme.toString());
            return null;
        }
    }
}
