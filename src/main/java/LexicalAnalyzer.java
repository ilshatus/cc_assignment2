import tokens.*;
import tokens.builders.Builder;
import tokens.enums.State;
import tokens.literals.*;

public class LexicalAnalyzer {
    private String inputCode; //text
    private Builder builders[]; // all tokens analyzers
    private int top; // index of current character

    private boolean errorInComment = false; //if preprocessing found error in comment structure
    private boolean first; // ask for first token or not

    private void addBuilders() { //add builders for all tokens
        builders = new Builder[11];
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
    }

    private void preprocess() {
        StringBuilder result = new StringBuilder();
        System.out.println("BEFORE preprocess : \n" + inputCode);
        for (int i = 0; i < inputCode.length() - 1; ++i) {
            if (inputCode.charAt(i) == '/' && inputCode.charAt(i + 1) == '*') {
                boolean flag = false;
                int pos = i;
                for (int j = i + 2; j < inputCode.length(); ++j) {
                    if (inputCode.charAt(j) == '/' && inputCode.charAt(j + 1) == '*') {
                        errorInComment = true;
                        return;
                    }
                    if (inputCode.charAt(j) == '*' && inputCode.charAt(j + 1) == '/') {
                        pos = j + 1;
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    System.out.println("not found end of comment");
                    errorInComment = true;
                    return;
                }
                i = pos; // skip whole comment
                continue;
            } else if (inputCode.charAt(i) == '/' && inputCode.charAt(i + 1) == '/') {
                boolean flag = false;
                int pos = i;
                for (int j = i + 2; j < inputCode.length(); ++j) {
                    if (inputCode.charAt(j) == '\n') {
                        pos = j + 1;
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    i = inputCode.length(); // skip till the end of file
                } else {
                    i = pos; // skip comment
                }
                continue;
            }
            result.append(inputCode.charAt(i)); //add character in not in comment
        }
        inputCode = result.toString();
        System.out.println("After preprocessing \n" + inputCode);
    }

    public LexicalAnalyzer(String inputCode) {
        this.inputCode = inputCode.replaceAll("\r", "");
        preprocess();
        addBuilders();
        top = 0;
        first = true;
    }

    Token getNextToken() {
        if (errorInComment) {
            if (first) {
                first = false;
                return new ErrorToken(" error in /* comment ", ' ');
            }
            return null;
        }
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
            int total = 0;
            for (int j = 0; j < builders.length; ++j) {
                if (builders[j] == null)
                    continue;
                total++;
                State x = builders[j].addNextChar(ch);
                if (x == State.MATCH) {
                    pos = i;
                    last = builders[j];
                } else if (x == State.NOT_MATCH) {
                    builders[j] = null;
                    --total;
                }
            }
            if (total == 0)
                break;

        }
        if (last == null) {
            return new ErrorToken("couldnt process starting from : ", inputCode.charAt(top));
        }

        last.clear();
        for (int i = top; i <= pos; ++i) {
            last.addNextChar(inputCode.charAt(i));
        }
        top = pos + 1; // move top for next token

        return last.build();
    }


}
