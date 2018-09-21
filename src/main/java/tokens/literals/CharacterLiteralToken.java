package tokens.literals;

import tokens.Token;
import tokens.enums.State;
import utils.CharacterUtil;

import java.util.Arrays;
import java.util.HashSet;

public class CharacterLiteralToken extends Token {
    private String lexeme;

    private CharacterLiteralToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_identifier(%s)", lexeme);
    }

    public static class Builder {
        private static final char[] escapeSeqChar = { 'b', 't', 'n', 'f', 'r', '\"', '\'', '\\' };
        private static final HashSet escapeSeqCharSet = new HashSet<>(Arrays.asList(escapeSeqChar));

        private StringBuilder lexeme;
        private int length;

        private boolean hasFirstQuote;

        private boolean hasBackSlash;

        private boolean hasUnicodeEscape;

        private int unicodeEscapeDigitsAmount;

        private State state;

        public Builder() {
            this.lexeme = new StringBuilder();
            this.length = 0;
            this.hasFirstQuote = false;
            this.state = State.PARTIALLY_MATCH;
            this.hasBackSlash = false;
            this.hasUnicodeEscape = false;
        }

        private void appendLexeme(char ch) {
            lexeme.append(ch);
            length++;
        }

        public State addNextChar(char ch) {
            // \n can't be the part of lexeme
            if (ch == '\n') {
                return State.NOT_MATCH;
            }
            if (ch == '\'') {
                if (length == 0) {
                    appendLexeme(ch);
                    hasFirstQuote = true;
                    return state = State.PARTIALLY_MATCH;
                }
                if (length > 0 && hasFirstQuote) {
                    appendLexeme(ch);
                    return state = State.MATCH;
                }
            }
            if (hasFirstQuote && state.equals(State.MATCH))
                return State.NOT_MATCH;
            if (hasFirstQuote) {
                if (ch == '\\' && !hasBackSlash) {
                    appendLexeme(ch);
                    hasBackSlash = true;
                    return state = State.PARTIALLY_MATCH;
                }
                if (ch == 'u' && lexeme.charAt(length - 1) == '\\') {
                    appendLexeme(ch);
                    hasUnicodeEscape = true;
                    unicodeEscapeDigitsAmount = 0;
                    return state = State.PARTIALLY_MATCH;
                }
                if (hasUnicodeEscape &&
                        unicodeEscapeDigitsAmount < 4 &&
                        CharacterUtil.isHexDigit(ch)) {
                    appendLexeme(ch);
                    unicodeEscapeDigitsAmount++;
                    return state = State.PARTIALLY_MATCH;
                }
            }
            return State.NOT_MATCH;
        }

        public CharacterLiteralToken build() {
            if (state.equals(State.MATCH))
                return new CharacterLiteralToken(lexeme.toString());
            return null;
        }
    }
}
