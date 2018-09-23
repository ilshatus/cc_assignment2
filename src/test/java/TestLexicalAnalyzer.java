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


    @Test
    public void test5() {
        String input = "object HelloWorld {\n" +
                "    def main(args: Array[String]) {\n" +
                "      println(\"Hello, world!\")\n" +
                "    }\n" +
                "  }";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_keyword(object)\n" +
                "T_identifier(HelloWorld)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(def)\n" +
                "T_identifier(main)\n" +
                "T_parentheses(()\n" +
                "T_identifier(args)\n" +
                "T_keyword(:)\n" +
                "T_identifier(Array)\n" +
                "T_parentheses([)\n" +
                "T_identifier(String)\n" +
                "T_parentheses(])\n" +
                "T_parentheses())\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"Hello, world!\")\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test6() {
        String input = "object HelloWorld2 extends Application {\n" +
                "  println(\"Hello, world!\")\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_keyword(object)\n" +
                "T_identifier(HelloWorld2)\n" +
                "T_keyword(extends)\n" +
                "T_identifier(Application)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"Hello, world!\")\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }


       @Test
       public void test7() {
           String input = "object abstractTypes extends Application {\n" +
                   "  abstract class Buffer {\n" +
                   "    type T; val element: T\n" +
                   "  }\n" +
                   "  abstract class SeqBuffer {\n" +
                   "    type T; val element: Seq[T]; def length = element.length\n" +
                   "  }\n" +
                   "  def newIntBuffer(el: Int) = new Buffer {\n" +
                   "    type T = Int; val element = el\n" +
                   "  }\n" +
                   "  def newIntBuffer(el: Int*) = new SeqBuffer {\n" +
                   "    type T = Int; val element = el\n" +
                   "  }\n" +
                   "  println(newIntBuffer(1).element)\n" +
                   "  println(newIntBuffer(1, 2, 3).length)\n" +
                   "}\n" +
                   "Printer-friendly versionPrinter-friendly version\n" ;
           LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
           Token token;
           String answer = "";
           while ((token = lexicalAnalyzer.getNextToken()) != null) {
               answer = answer + token.toString() + "\n";
           }
           String res = "T_keyword(object)\n" +
                   "T_identifier(abstractTypes)\n" +
                   "T_keyword(extends)\n" +
                   "T_identifier(Application)\n" +
                   "T_parentheses({)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(abstract)\n" +
                   "T_keyword(class)\n" +
                   "T_identifier(Buffer)\n" +
                   "T_parentheses({)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(type)\n" +
                   "T_identifier(T)\n" +
                   "T_delimiter(comma)\n" +
                   "T_keyword(val)\n" +
                   "T_identifier(element)\n" +
                   "T_keyword(:)\n" +
                   "T_identifier(T)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_parentheses(})\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(abstract)\n" +
                   "T_keyword(class)\n" +
                   "T_identifier(SeqBuffer)\n" +
                   "T_parentheses({)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(type)\n" +
                   "T_identifier(T)\n" +
                   "T_delimiter(comma)\n" +
                   "T_keyword(val)\n" +
                   "T_identifier(element)\n" +
                   "T_keyword(:)\n" +
                   "T_identifier(Seq)\n" +
                   "T_parentheses([)\n" +
                   "T_identifier(T)\n" +
                   "T_parentheses(])\n" +
                   "T_delimiter(comma)\n" +
                   "T_keyword(def)\n" +
                   "T_identifier(length)\n" +
                   "T_keyword(=)\n" +
                   "T_identifier(element)\n" +
                   "T_delimiter(dot)\n" +
                   "T_identifier(length)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_parentheses(})\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(def)\n" +
                   "T_identifier(newIntBuffer)\n" +
                   "T_parentheses(()\n" +
                   "T_identifier(el)\n" +
                   "T_keyword(:)\n" +
                   "T_identifier(Int)\n" +
                   "T_parentheses())\n" +
                   "T_keyword(=)\n" +
                   "T_keyword(new)\n" +
                   "T_identifier(Buffer)\n" +
                   "T_parentheses({)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(type)\n" +
                   "T_identifier(T)\n" +
                   "T_keyword(=)\n" +
                   "T_identifier(Int)\n" +
                   "T_delimiter(comma)\n" +
                   "T_keyword(val)\n" +
                   "T_identifier(element)\n" +
                   "T_keyword(=)\n" +
                   "T_identifier(el)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_parentheses(})\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(def)\n" +
                   "T_identifier(newIntBuffer)\n" +
                   "T_parentheses(()\n" +
                   "T_identifier(el)\n" +
                   "T_keyword(:)\n" +
                   "T_identifier(Int)\n" +
                   "T_identifier(*)\n" +
                   "T_parentheses())\n" +
                   "T_keyword(=)\n" +
                   "T_keyword(new)\n" +
                   "T_identifier(SeqBuffer)\n" +
                   "T_parentheses({)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(type)\n" +
                   "T_identifier(T)\n" +
                   "T_keyword(=)\n" +
                   "T_identifier(Int)\n" +
                   "T_delimiter(comma)\n" +
                   "T_keyword(val)\n" +
                   "T_identifier(element)\n" +
                   "T_keyword(=)\n" +
                   "T_identifier(el)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_parentheses(})\n" +
                   "T_delimiter(new_line)\n" +
                   "T_identifier(println)\n" +
                   "T_parentheses(()\n" +
                   "T_identifier(newIntBuffer)\n" +
                   "T_parentheses(()\n" +
                   "T_literal_integer(1)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(dot)\n" +
                   "T_identifier(element)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(new_line)\n" +
                   "T_identifier(println)\n" +
                   "T_parentheses(()\n" +
                   "T_identifier(newIntBuffer)\n" +
                   "T_parentheses(()\n" +
                   "T_literal_integer(1)\n" +
                   "T_delimiter(comma)\n" +
                   "T_literal_integer(2)\n" +
                   "T_delimiter(comma)\n" +
                   "T_literal_integer(3)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(dot)\n" +
                   "T_identifier(length)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(new_line)\n" +
                   "T_parentheses(})\n" +
                   "T_delimiter(new_line)\n" +
                   "T_identifier(Printer)\n" +
                   "T_identifier(-)\n" +
                   "T_identifier(friendly)\n" +
                   "T_identifier(versionPrinter)\n" +
                   "T_identifier(-)\n" +
                   "T_identifier(friendly)\n" +
                   "T_identifier(version)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_end_of_file()\n";
           Assert.assertTrue(answer.equals( res));
       }

       @Test
       public void test8() {
           String input = "/** Bigint's can be used seamlessly */\n" +
                   "object bigint extends Application {\n" +
                   "  def factorial(n: BigInt): BigInt =\n" +
                   "    if (n == 0) 1 else n * factorial(n-1)\n" +
                   "\n" +
                   "  val f50 = factorial(50); val f49 = factorial(49)\n" +
                   "  println(\"50! = \" + f50)\n" +
                   "  println(\"49! = \" + f49)\n" +
                   "  println(\"50!/49! = \" + (f50 / f49))\n" +
                   "}";
           LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
           Token token;
           String answer = "";
           while ((token = lexicalAnalyzer.getNextToken()) != null) {
               answer = answer + token.toString() + "\n";
           }
           String res = "T_comment(/** Bigint's can be used seamlessly */)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(object)\n" +
                   "T_identifier(bigint)\n" +
                   "T_keyword(extends)\n" +
                   "T_identifier(Application)\n" +
                   "T_parentheses({)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(def)\n" +
                   "T_identifier(factorial)\n" +
                   "T_parentheses(()\n" +
                   "T_identifier(n)\n" +
                   "T_keyword(:)\n" +
                   "T_identifier(BigInt)\n" +
                   "T_parentheses())\n" +
                   "T_keyword(:)\n" +
                   "T_identifier(BigInt)\n" +
                   "T_keyword(=)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(if)\n" +
                   "T_parentheses(()\n" +
                   "T_identifier(n)\n" +
                   "T_identifier(==)\n" +
                   "T_literal_integer(0)\n" +
                   "T_parentheses())\n" +
                   "T_literal_integer(1)\n" +
                   "T_keyword(else)\n" +
                   "T_identifier(n)\n" +
                   "T_identifier(*)\n" +
                   "T_identifier(factorial)\n" +
                   "T_parentheses(()\n" +
                   "T_identifier(n)\n" +
                   "T_literal_integer(-1)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(new_line)\n" +
                   "T_delimiter(new_line)\n" +
                   "T_keyword(val)\n" +
                   "T_identifier(f50)\n" +
                   "T_keyword(=)\n" +
                   "T_identifier(factorial)\n" +
                   "T_parentheses(()\n" +
                   "T_literal_integer(50)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(comma)\n" +
                   "T_keyword(val)\n" +
                   "T_identifier(f49)\n" +
                   "T_keyword(=)\n" +
                   "T_identifier(factorial)\n" +
                   "T_parentheses(()\n" +
                   "T_literal_integer(49)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(new_line)\n" +
                   "T_identifier(println)\n" +
                   "T_parentheses(()\n" +
                   "T_literal_string(\"50! = \")\n" +
                   "T_identifier(+)\n" +
                   "T_identifier(f50)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(new_line)\n" +
                   "T_identifier(println)\n" +
                   "T_parentheses(()\n" +
                   "T_literal_string(\"49! = \")\n" +
                   "T_identifier(+)\n" +
                   "T_identifier(f49)\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(new_line)\n" +
                   "T_identifier(println)\n" +
                   "T_parentheses(()\n" +
                   "T_literal_string(\"50!/49! = \")\n" +
                   "T_identifier(+)\n" +
                   "T_parentheses(()\n" +
                   "T_identifier(f50)\n" +
                   "T_identifier(/)\n" +
                   "T_identifier(f49)\n" +
                   "T_parentheses())\n" +
                   "T_parentheses())\n" +
                   "T_delimiter(new_line)\n" +
                   "T_parentheses(})\n" +
                   "T_end_of_file()\n";
           Assert.assertTrue(answer.equals( res));
       }



       /*   @Test
       public void test() {
           String input = ;
           LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
           Token token;
           String answer = "";
           while ((token = lexicalAnalyzer.getNextToken()) != null) {
               answer = answer + token.toString() + "\n";
           }
           String res =
           Assert.assertTrue(answer.equals( res));
       }
   */






}
