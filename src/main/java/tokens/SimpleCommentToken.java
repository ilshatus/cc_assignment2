package tokens;

import tokens.builders.SimpleRegexBuilder;
import tokens.enums.State;

import java.util.regex.Pattern;

public class SimpleCommentToken extends Token {
    private String lexeme;

    private SimpleCommentToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_comment(%s)", lexeme);
    }

    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp = "^[/]{2}.*$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp, Pattern.MULTILINE);
        }

        @Override
        public SimpleCommentToken build() {
            if (state.equals(State.MATCH))
                return new SimpleCommentToken(lexeme.toString());
            return null;
        }
    }
}
