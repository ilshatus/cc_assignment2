package tokens.literals;

import tokens.Token;
import tokens.enums.State;
import utils.CharacterUtil;

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

    public static class Builder {

        private StringBuilder lexeme;
        private int length;

        private State state;

        private boolean hasDot;
        private boolean hasExponent;

        public Builder() {
            lexeme = new StringBuilder();
            length = 0;
            hasDot = false;
            hasExponent = false;
            state = State.PARTIALLY_MATCH;
        }

        private void appendLexeme(char ch) {
            lexeme.append(ch);
            length++;
        }

        private boolean isSign(char ch) {
            return ch == '+' || ch == '-';
        }

        private boolean isExponent(char ch) {
            return ch == 'E' || ch == 'e';
        }

        public State addNextChar(char ch) {
            if (CharacterUtil.isDigit(ch)) {
                appendLexeme(ch);
                return state = State.MATCH;
            }
            if (isSign(ch)) {
                if (length == 0) {
                    appendLexeme(ch);
                    return state = State.PARTIALLY_MATCH;
                }
                if (isExponent(lexeme.charAt(length - 1))) {
                    appendLexeme(ch);
                    return state = State.PARTIALLY_MATCH;
                }
                return State.NOT_MATCH;
            }
            if (isExponent(ch)) {
                if (length == 0) {
                    return State.NOT_MATCH;
                }
                if (!hasExponent &&
                        CharacterUtil.isDigit(lexeme.charAt(length - 1))) {
                    appendLexeme(ch);
                    hasExponent = true;
                    return state = State.PARTIALLY_MATCH;
                }
                return State.NOT_MATCH;
            }
            if (ch == '.') {
                if (length == 0 ||
                        (length == 1 && isSign(lexeme.charAt(length - 1))) ||
                        (!hasDot && !hasExponent)) {
                    appendLexeme(ch);
                    hasDot = true;
                    return state = State.PARTIALLY_MATCH;
                }
                return State.NOT_MATCH;
            }
            return State.NOT_MATCH;
        }

        public NumericLiteralToken build() {
            if (state.equals(State.MATCH)) {
                Type type;
                if (hasDot || hasExponent) type = Type.FLOAT;
                else type = Type.INTEGER;
                return new NumericLiteralToken(type, lexeme.toString());
            }
            return null;
        }
    }
}
