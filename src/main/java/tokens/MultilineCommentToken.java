package tokens;

import tokens.enums.State;

public class MultilineCommentToken extends Token {
    private String lexeme;

    private MultilineCommentToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_comment(%s)", lexeme);
    }

    public static class Builder implements tokens.builders.Builder {

        private StringBuilder lexeme;
        private int length;
        private int openedComments;

        private State state;

        public Builder() {
            this.lexeme = new StringBuilder();
            this.length = 0;
            this.state = State.PARTIALLY_MATCH;
            this.openedComments = 0;
        }

        private void appendLexeme(char ch) {
            lexeme.append(ch);
            length++;
        }

        @Override
        public void clear() {
            this.lexeme = new StringBuilder();
            this.length = 0;
            this.state = State.PARTIALLY_MATCH;
            this.openedComments = 0;
        }

        @Override
        public State addNextChar(char ch) {
            if (state.equals(State.MATCH)) return State.NOT_MATCH;
            if (length == 0) {
                if (ch == '/') {
                    appendLexeme(ch);
                    return state;
                }
                return State.NOT_MATCH;
            }
            if (length == 1) {
                if (ch == '*') {
                    appendLexeme(ch);
                    openedComments++;
                    return state;
                }
                return State.NOT_MATCH;
            }
            if (ch == '*' && lexeme.charAt(length - 1) == '/') {
                appendLexeme(ch);
                openedComments++;
                return state;
            }
            if (ch == '/' && lexeme.charAt(length - 1) == '*' && length > 2) {
                appendLexeme(ch);
                openedComments--;
                if (openedComments == 0) {
                    return state = State.MATCH;
                }
                return state;
            }
            appendLexeme(ch);
            return state;
        }

        @Override
        public MultilineCommentToken build() {
            if (state.equals(State.MATCH))
                return new MultilineCommentToken(lexeme.toString());
            return null;
        }
    }
}
