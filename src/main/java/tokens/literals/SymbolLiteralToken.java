package tokens.literals;

import tokens.PlainIdentifierToken;
import tokens.Token;
import tokens.enums.State;

public class SymbolLiteralToken extends Token {
    private String lexeme;

    private SymbolLiteralToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_literal_symbol(%s)", lexeme);
    }

    public static class Builder implements tokens.builders.Builder {

        private State state;

        private PlainIdentifierToken.Builder plainIdBuilder;

        private boolean hasQuote;

        public Builder() {
            this.hasQuote = false;
            this.plainIdBuilder = new PlainIdentifierToken.Builder();
            this.state = State.PARTIALLY_MATCH;
        }

        @Override
        public State addNextChar(char ch) {
            if (ch == '\'') {
                hasQuote = true;
                return state = State.PARTIALLY_MATCH;
            }
            if (hasQuote) {
                State plainIdState = plainIdBuilder.addNextChar(ch);
                if (plainIdState != State.NOT_MATCH) {
                    state = plainIdState;
                }
                return plainIdState;
            }
            return State.NOT_MATCH;
        }

        @Override
        public SymbolLiteralToken build() {
            if (state.equals(State.MATCH))
                return new SymbolLiteralToken('\'' + plainIdBuilder.getLexeme().toString());
            return null;
        }
    }
}
