import org.junit.Assert;
import org.junit.Test;
import tokens.ParenthesesToken;
import tokens.builders.Builder;
import tokens.enums.State;
import tokens.literals.SymbolLiteralToken;
import tokens.Token;

import java.sql.SQLOutput;

public class TestLexicalAnalyzer {
    @Test
    public void test1() {
        String input = "/* asd /* asdas */";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_error(message=\"Incorrect comment\", line=1)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken() == null);
        Assert.assertTrue(lexicalAnalyzer.getNextToken() == null);
    }

    @Test
    public void test2() {
        String input = "val a = b";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_keyword(val)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_identifier(a)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_keyword(=)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_identifier(b)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_end_of_file()"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken() == null);
    }

    @Test
    public void test3() {
        String input = "Object main def class if els";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_identifier(Object)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_identifier(main)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_keyword(def)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_keyword(class)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_keyword(if)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_identifier(els)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_end_of_file()"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken() == null);
    }

    @Test
    public void test4() {
        String input = "123\"asdasd\"";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_literal_integer(123)"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_literal_string(\"asdasd\")"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken().toString().equals("T_end_of_file()"));
        Assert.assertTrue(lexicalAnalyzer.getNextToken() == null);
        Assert.assertTrue(lexicalAnalyzer.getNextToken() == null);
        Assert.assertTrue(lexicalAnalyzer.getNextToken() == null);
    }
}
