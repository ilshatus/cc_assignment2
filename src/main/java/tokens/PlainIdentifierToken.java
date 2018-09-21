package tokens;

import tokens.enums.State;
import utils.CharacterUtil;

public class PlainIdentifierToken extends Token {

    private String lexeme;

    private PlainIdentifierToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_identifier(%s)", lexeme);
    }

    public static class Builder implements tokens.builders.Builder {

        private StringBuilder lexeme;
        private int length;

        private boolean hasOperator;

        private State state;

        public Builder() {
            this.lexeme = new StringBuilder();
            this.length = 0;
            this.hasOperator = false;
            this.state = State.PARTIALLY_MATCH;
        }

        public StringBuilder getLexeme() {
            return lexeme;
        }

        public void setLexeme(StringBuilder lexeme) {
            this.lexeme = lexeme;
        }

        private void appendLexeme(char ch) {
            lexeme.append(ch);
            length++;
        }

        @Override
        public State addNextChar(char ch) {
            if (CharacterUtil.isOperatorChar(ch)) {
                // first operator char at the start of lexeme
                if (length == 0) {
                    appendLexeme(ch);
                    hasOperator = true;
                    return state = State.MATCH;
                }
                // operator char may be preceded only by another operator char or underscore
                if (CharacterUtil.isOperatorChar(lexeme.charAt(length - 1)) ||
                        lexeme.charAt(length - 1) == '_') {
                    appendLexeme(ch);
                    hasOperator = true;
                    return state = State.MATCH;
                }
                return State.NOT_MATCH;
            }
            // after operator char may not go any symbols except operator chars
            if (hasOperator) {
                return State.NOT_MATCH;
            }
            // otherwise there are may go letters + ($ and _)
            if (CharacterUtil.isLetter(ch)) {
                appendLexeme(ch);
                return state = State.MATCH;
            }
            // or digits if they are not at the beginning
            if (CharacterUtil.isDigit(ch) && length > 0) {
                appendLexeme(ch);
                return state = State.MATCH;
            }
            return State.NOT_MATCH;
        }

        @Override
        public PlainIdentifierToken build() {
            if (state.equals(State.MATCH))
                return new PlainIdentifierToken(lexeme.toString());
            return null;
        }
    }
}
