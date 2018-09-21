package tokens;

import tokens.builders.SimpleRegexBuilder;
import tokens.enums.State;

import java.util.regex.Pattern;

public class DelimiterToken extends Token {
    private enum Type {
        DOT,
        SEMICOLON,
        COMMA,
        NEW_LINE
    }

    private Type type;

    private DelimiterToken(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("T_delimiter(%s)",
                type.toString().toLowerCase());
    }


    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp =
                "^[.;,\\n]$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp, Pattern.MULTILINE);
        }

        @Override
        public DelimiterToken build() {
            if (state.equals(State.MATCH)) {
                String lexemeS = lexeme.toString();
                Type type;
                if (lexemeS.equals(";"))
                    type = Type.SEMICOLON;
                if (lexemeS.equals("\n"))
                    type = Type.NEW_LINE;
                else if (lexemeS.equals("."))
                    type = Type.DOT;
                else
                    type = Type.COMMA;
                return new DelimiterToken(type);
            }
            return null;
        }
    }
}
