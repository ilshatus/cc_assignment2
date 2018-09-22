import tokens.*;
import tokens.builders.Builder;
import tokens.enums.State;
import tokens.literals.*;

public class LexicalAnalyzer {
    private String inputCode; //text
    private Builder builders[]; // all tokens analyzers
    private int top; // index of current character

    private void addBuilders() { //add builders for all tokens
        builders = new Builder[13];
        builders[0] = new PlainIdentifierToken.Builder();
        builders[1] = new LogicalLiteralToken.Builder();
        builders[2] = new MultilineStringLiteralToken.Builder();
        builders[3] = new NumericLiteralToken.Builder();
        builders[4] = new StringLiteralToken.Builder();
        builders[5] = new SymbolLiteralToken.Builder();
        builders[6] = new BackQuoteIdentifierToken.Builder();
        builders[7] = new DelimiterToken.Builder();
        builders[8] = new KeywordToken.Builder();
        builders[9] = new CharacterLiteralToken.Builder();
        builders[10] = new ParenthesesToken.Builder();
        builders[11] = new MultilineCommentToken.Builder();
        builders[12] = new SimpleCommentToken.Builder();
    }

    public LexicalAnalyzer(String inputCode) {
        this.inputCode = inputCode.replaceAll("\r", "");
        addBuilders();
        top = 0;
    }

    Token getNextToken() {
        if (top == inputCode.length()) {
            top++;
            return new EndOfFileToken();
        }
        if (top > inputCode.length())
            return null;
        addBuilders();
        while (top < inputCode.length() && inputCode.charAt(top) == ' ') {
            top++;
        }
        Builder last = null;
        int pos = top;
        for (int i = top; i < inputCode.length(); ++i) {
            char ch = inputCode.charAt(i);
            int total = 0; // total correct or partially correct token builders
            for (int j = 0; j < builders.length; ++j) {
                if (builders[j] == null) // skip bad tokens
                    continue;
                total++;
                State x = builders[j].addNextChar(ch); //add next character
                if (x == State.MATCH) { // if token identifies the string
                    pos = i; // remember last position
                    last = builders[j]; //remember token
                } else if (x == State.NOT_MATCH) {
                    builders[j] = null; // delete this token identifier
                    --total; // dont count this one
                }
            }
            if (total == 0) //none recognizes
                break;

        }

        if (builders[11] != null &&
                builders[11].getState().equals(State.PARTIALLY_MATCH)) {
            top = inputCode.length() + 1;
            return new ErrorToken("Incorrect comment");
        }

        if (last == null) {
            int r = Math.min(inputCode.length(), top + 10);
            String error_text = "on position "+top
                    + " first characters are "+inputCode.substring(top,r);
            top = inputCode.length() + 1;
            return new ErrorToken("Token not recognized " + error_text);
        }

        last.clear();
        for (int i = top; i <= pos; ++i) {
            last.addNextChar(inputCode.charAt(i)); // add new token
        }
        top = pos + 1; // move top for next token

        return last.build();
    }


}
