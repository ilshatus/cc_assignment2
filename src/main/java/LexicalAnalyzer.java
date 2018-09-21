import tokens.*;
import tokens.builders.Builder;
import tokens.enums.State;
import tokens.literals.*;

public class LexicalAnalyzer {
    private String inputCode;
    private Builder builders[];
    private int top = 0;

    private void addBuilders() {
        builders = new Builder[11];
        builders[0] = new CharacterLiteralToken.Builder();
        builders[1] = new LogicalLiteralToken.Builder();
        builders[2] = new MultilineStringLiteralToken.Builder();
        builders[3] = new NumericLiteralToken.Builder();
        builders[4] = new StringLiteralToken.Builder();
        builders[5] = new SymbolLiteralToken.Builder();
        builders[6] = new BackQuoteIdentifierToken.Builder();
        builders[7] = new DelimiterToken.Builder();
        builders[8] = new KeywordToken.Builder();
        builders[9] = new PlainIdentifierToken.Builder();
        builders[10] = new ParenthesesToken.Builder();
    }

    private void preprocess(){
        StringBuilder result = new StringBuilder();
        System.out.println("HERE \n" + inputCode);
        for(int i = 0;i<inputCode.length()-1;++i){
            if(inputCode.charAt(i)=='/' && inputCode.charAt(i+1)=='*'){
                boolean flag = false;
                int pos = i;
                for(int j = i+2;j<inputCode.length();++j){
                    if(inputCode.charAt(j)=='*' && inputCode.charAt(j+1)=='/'){
                        pos = j+1;
                        flag = true;
                        break;
                    }
                }

                if(!flag){
                    System.out.println("not found end of comment");
                }
                i = pos;
                continue;
            } else if(inputCode.charAt(i)=='/' && inputCode.charAt(i+1)=='/'){
                boolean flag = false;
                int pos = i;
                for(int j = i+2;j<inputCode.length();++j){
                    if(inputCode.charAt(j)=='\n'){
                        pos = j+1;
                        flag = true;
                        break;
                    }
                }

                if(!flag){
                    i = inputCode.length();
                } else{
                    i = pos;
                }
                continue;
            }
            result.append(inputCode.charAt(i));
        }
        inputCode = result.toString();
        System.out.println("AFTER \n" + inputCode);
    }

    public LexicalAnalyzer(String inputCode) {
        this.inputCode = inputCode.replaceAll("\r", "");
        preprocess();
        addBuilders();
        top = 0;
    }

    Token getNextToken() {
        addBuilders();

        while(top < inputCode.length()){
            char c = inputCode.charAt(top);
            if(c==' ' || c =='\n'){
                System.out.println("skip " + c);
                top++;
            } else{
                break;
            }
        }
        Builder last = null;
        int pos = top;
        System.out.println("start decode " + top);
        for(int i=top;i<inputCode.length();++i){
            char ch = inputCode.charAt(i);
            System.out.println("next char [" + ch+"]");
            int total = 0;
            for(int j=0;j<builders.length;++j){
                if(builders[j] == null)
                    continue;
                total++;
                State x = builders[j].addNextChar(ch);
                System.out.println(ch + " buil "+j+" got " + x);
                if(x == State.MATCH){
                    System.out.println("full match " + j + " on "+i);
                    pos = i;
                    last = builders[j];
                } else if(x == State.NOT_MATCH){
                    builders[j] = null;
                    --total;
                }
            }
            if(total == 0)
                break;

        }

        if(last == null)
         System.out.println("last null");

        if(last == null && top<inputCode.length()){
            System.out.println("couldnt identify");
        }
        top = pos + 1; // move top for next token

        if(last == null)
            return null;
        System.out.println("HERE ");
        return last.build();
    }


}
