import tokens.BackQuoteIdentifierToken;
import tokens.DelimiterToken;
import tokens.KeywordToken;
import tokens.Token;
import tokens.builders.Builder;
import tokens.literals.CharacterLiteralToken;
import tokens.literals.MultilineStringLiteralToken;
import tokens.literals.NumericLiteralToken;
import tokens.literals.StringLiteralToken;

import java.io.*;

import static tokens.enums.State.NOT_MATCH;

public class Main {

    private static String readFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        char[] input = new char[(int) file.length()];
        if (reader.read(input) == -1) {
            System.out.println("Error");
        }
        return String.copyValueOf(input);
    }


    public static void main(String[] args) {
        /*
         Пример работы
         */

        String s = "⇒";
        Builder builder = new KeywordToken.Builder();
        for (int i = 0; i < s.length(); i++) {
            if (builder.addNextChar(s.charAt(i)).equals(NOT_MATCH)) {
                System.out.println("bad");
            }
        }


        /////////////////////////////////////////////////

        System.out.println(builder.build());
        try {
            String input = readFile("in.txt");
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
            Token token;
            while ((token = lexicalAnalyzer.getNextToken()) != null) {
                System.out.println(token.toString());
            }
        } catch (IOException e) {
            System.out.println("Can't read file");
        }
    }
}
