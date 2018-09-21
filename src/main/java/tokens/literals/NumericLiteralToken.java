package tokens.literals;

import tokens.Token;
import tokens.builders.SimpleRegexBuilder;
import tokens.enums.State;
import utils.CharacterUtil;

import java.util.regex.Pattern;

public class NumericLiteralToken extends Token {
    private enum Type {
        INTEGER, FLOAT
    }

    private Type type;

    private String lexeme;

    private NumericLiteralToken(Type type, String lexeme) {
        this.lexeme = lexeme;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("T_literal_%s(%s)",
                type.toString().toLowerCase(), lexeme);
    }

    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp =
                "^[+-]?(([1-9]\\d*)|(\\.\\d+)|([1-9]\\d*)(\\.\\d+))([eE][+-]?\\d+)?$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp, Pattern.MULTILINE);
        }

        @Override
        public NumericLiteralToken build() {
            if (state.equals(State.MATCH)) {
                Type type;
                String lexemeS = lexeme.toString();
                if (lexemeS.contains("e") ||
                        lexemeS.contains("E") ||
                        lexemeS.contains(".")) {
                    type = Type.FLOAT;
                } else {
                    type = Type.INTEGER;
                }
                return new NumericLiteralToken(type, lexemeS);
            }
            return null;
        }
    }
}
