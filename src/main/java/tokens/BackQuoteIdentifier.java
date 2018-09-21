package tokens;

import tokens.enums.State;

public class BackQuoteIdentifier extends Token {
    private String lexeme;

    private BackQuoteIdentifier(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_identifier(%s)", lexeme);
    }

    public static class Builder {

        private StringBuilder lexeme;
        private int length;

        private boolean hasFirstBackQuote;

        private State state;

        public Builder() {
            this.lexeme = new StringBuilder();
            this.length = 0;
            this.hasFirstBackQuote = false;
            this.state = State.PARTIALLY_MATCH;
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
            if (ch == '`') {
                // first back quote at the start of lexeme
                if (length == 0) {
                    appendLexeme(ch);
                    hasFirstBackQuote = true;
                    return state = State.PARTIALLY_MATCH;
                }
                // last back quote at the end of back quote
                if (length > 0 && hasFirstBackQuote) {
                    appendLexeme(ch);
                    return state = State.MATCH;
                }
                return State.NOT_MATCH;
            }
            // identifier with back quotes is already complete
            if (hasFirstBackQuote && state.equals(State.MATCH))
                return State.NOT_MATCH;
            // any symbol acceptable inside back quotes
            if (hasFirstBackQuote) {
                appendLexeme(ch);
                return state;
            }
            return State.NOT_MATCH;
        }

        public BackQuoteIdentifier build() {
            if (state.equals(State.MATCH))
                return new BackQuoteIdentifier(lexeme.toString());
            return null;
        }
    }
}
