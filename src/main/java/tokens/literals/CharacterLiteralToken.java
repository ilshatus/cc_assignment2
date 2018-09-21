package tokens.literals;

import tokens.Token;
import tokens.enums.State;
import tokens.builders.SimpleRegexBuilder;

import java.util.regex.Pattern;

public class CharacterLiteralToken extends Token {
    private String lexeme;

    private CharacterLiteralToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_literal_character(%s)", lexeme);
    }


    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp =
                "^'([^\\\\\\n\\r]?|\\\\(u(\\d|[a-f]|[A-F]){4}|[0-7]{3}|[btnfr\\\\'\"]))'$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp);
        }

        @Override
        public CharacterLiteralToken build() {
            if (state.equals(State.MATCH))
                return new CharacterLiteralToken(lexeme.toString());
            return null;
        }
    }
}
