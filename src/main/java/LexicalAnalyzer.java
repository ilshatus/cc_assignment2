import tokens.*;
import tokens.builders.Builder;
import tokens.enums.State;
import tokens.literals.*;
import utils.CharacterUtil;

public class LexicalAnalyzer {
    private String inputCode; //text
    private Builder builders[]; // all tokens analyzers
    private int top; // index of current character
    private int line;

    private void addBuilders() { //add builders for all tokens
        builders = new Builder[14];
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
        builders[11] = new XmlToken.Builder();
        builders[12] = new SimpleCommentToken.Builder();
        builders[13] = new MultilineCommentToken.Builder();
    }

    public LexicalAnalyzer(String inputCode) {
        this.inputCode = inputCode.replaceAll("\r", "");
        addBuilders();
        line = 1;
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
        while (top < inputCode.length() &&
                CharacterUtil.isWhiteSpace(inputCode.charAt(top))) {
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
            return new ErrorToken("Token not recognized", line);
        }

        if (builders[13] != null &&
                builders[13].getState().equals(State.PARTIALLY_MATCH)) {
            top = inputCode.length() + 1;
            return new ErrorToken("Incorrect comment", line);
        }

        if (last == null) {
            top = inputCode.length() + 1;
            return new ErrorToken("Token not recognized", line);
        }

        last.clear();
        for (int i = top; i <= pos; ++i) {
            if (inputCode.charAt(i) == '\n')
                line++;
            last.addNextChar(inputCode.charAt(i)); // add new token
        }
        top = pos + 1; // move top for next token

        return last.build();
    }
}
