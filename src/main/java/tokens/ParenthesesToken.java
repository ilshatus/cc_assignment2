package tokens;

import tokens.builders.SimpleRegexBuilder;
import tokens.enums.State;

import java.util.regex.Pattern;

public class ParenthesesToken extends Token {
    private String lexeme;

    private ParenthesesToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_parentheses(%s)", lexeme);
    }


    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp =
                "^[(){}\\[\\]]$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp);
        }

        @Override
        public ParenthesesToken build() {
            System.out.println("par "+ state);
            if (state.equals(State.MATCH)) {
                return new ParenthesesToken(lexeme.toString());
            }
            return null;
        }
    }
}
