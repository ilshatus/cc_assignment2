import tokens.Token;

public class LexicalAnalyzer {
    private String inputCode;

    public LexicalAnalyzer(String inputCode) {
        this.inputCode = inputCode.replaceAll("\r", "");
    }

    Token getNextToken() {

        return null;
    }


}
