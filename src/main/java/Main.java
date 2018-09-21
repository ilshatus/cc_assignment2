import tokens.*;
import tokens.builders.Builder;
import tokens.literals.*;

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