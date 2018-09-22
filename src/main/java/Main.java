import tokens.*;

import java.io.*;

public class Main {
    private static final String INPUT_FILE_NAME = "in.txt";
    private static final String OUTPUT_FILE_NAME = "out.txt";

    private static String readFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        char[] input = new char[(int) file.length()];
        if (reader.read(input) == -1) {
            throw new IOException("Can't read file");
        }
        return String.copyValueOf(input);
    }


    public static void main(String[] args) {
        try {
            String input = readFile(INPUT_FILE_NAME);
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
            PrintWriter writer = new PrintWriter(OUTPUT_FILE_NAME);
            Token token;
            while ((token = lexicalAnalyzer.getNextToken()) != null) {
                writer.println(token);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}