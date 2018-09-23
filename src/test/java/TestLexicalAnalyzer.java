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
                "Printer-friendly versionPrinter-friendly version\n";
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
        Assert.assertTrue(answer.equals(res));
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
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test9() {
        String input = "object Main {\n" +
                "  def main(args: Array[String]) {\n" +
                "    val res = for (a <- args) yield a.toUpperCase\n" +
                "    println(\"Arguments: \" + res.toString)\n" +
                "  }\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_keyword(object)\n" +
                "T_identifier(Main)\n" +
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
                "T_keyword(val)\n" +
                "T_identifier(res)\n" +
                "T_keyword(=)\n" +
                "T_keyword(for)\n" +
                "T_parentheses(()\n" +
                "T_identifier(a)\n" +
                "T_keyword(<-)\n" +
                "T_identifier(args)\n" +
                "T_parentheses())\n" +
                "T_keyword(yield)\n" +
                "T_identifier(a)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(toUpperCase)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"Arguments: \")\n" +
                "T_identifier(+)\n" +
                "T_identifier(res)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(toString)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test10() {
        String input = "/* Adding ! as a method on int's */\n" +
                "object extendBuiltins extends Application {\n" +
                "  def fact(n: Int): BigInt =\n" +
                "    if (n == 0) 1 else fact(n-1) * n\n" +
                "  class Factorizer(n: Int) {\n" +
                "    def ! = fact(n)\n" +
                "  }\n" +
                "  implicit def int2fact(n: Int) = new Factorizer(n)\n" +
                "\n" +
                "  println(\"10! = \" + (10!))\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_comment(/* Adding ! as a method on int's */)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(object)\n" +
                "T_identifier(extendBuiltins)\n" +
                "T_keyword(extends)\n" +
                "T_identifier(Application)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(def)\n" +
                "T_identifier(fact)\n" +
                "T_parentheses(()\n" +
                "T_identifier(n)\n" +
                "T_keyword(:)\n" +
                "T_identifier(Int)\n" +
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
                "T_identifier(fact)\n" +
                "T_parentheses(()\n" +
                "T_identifier(n)\n" +
                "T_literal_integer(-1)\n" +
                "T_parentheses())\n" +
                "T_identifier(*)\n" +
                "T_identifier(n)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(class)\n" +
                "T_identifier(Factorizer)\n" +
                "T_parentheses(()\n" +
                "T_identifier(n)\n" +
                "T_keyword(:)\n" +
                "T_identifier(Int)\n" +
                "T_parentheses())\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(def)\n" +
                "T_identifier(!)\n" +
                "T_keyword(=)\n" +
                "T_identifier(fact)\n" +
                "T_parentheses(()\n" +
                "T_identifier(n)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(implicit)\n" +
                "T_keyword(def)\n" +
                "T_identifier(int2fact)\n" +
                "T_parentheses(()\n" +
                "T_identifier(n)\n" +
                "T_keyword(:)\n" +
                "T_identifier(Int)\n" +
                "T_parentheses())\n" +
                "T_keyword(=)\n" +
                "T_keyword(new)\n" +
                "T_identifier(Factorizer)\n" +
                "T_parentheses(()\n" +
                "T_identifier(n)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"10! = \")\n" +
                "T_identifier(+)\n" +
                "T_parentheses(()\n" +
                "T_literal_integer(10)\n" +
                "T_identifier(!)\n" +
                "T_parentheses())\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test11() {
        String input = "/* Defines a new method 'sort' for array objects */\n" +
                "object implicits extends Application {\n" +
                "  implicit def arrayWrapper[A : ClassManifest](x: Array[A]) =\n" +
                "    new {\n" +
                "      def sort(p: (A, A) => Boolean) = {\n" +
                "        util.Sorting.stableSort(x, p); x\n" +
                "      }\n" +
                "    }\n" +
                "  val x = Array(2, 3, 1, 4)\n" +
                "  println(\"x = \"+ x.sort((x: Int, y: Int) => x < y))\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_comment(/* Defines a new method 'sort' for array objects */)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(object)\n" +
                "T_identifier(implicits)\n" +
                "T_keyword(extends)\n" +
                "T_identifier(Application)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(implicit)\n" +
                "T_keyword(def)\n" +
                "T_identifier(arrayWrapper)\n" +
                "T_parentheses([)\n" +
                "T_identifier(A)\n" +
                "T_keyword(:)\n" +
                "T_identifier(ClassManifest)\n" +
                "T_parentheses(])\n" +
                "T_parentheses(()\n" +
                "T_identifier(x)\n" +
                "T_keyword(:)\n" +
                "T_identifier(Array)\n" +
                "T_parentheses([)\n" +
                "T_identifier(A)\n" +
                "T_parentheses(])\n" +
                "T_parentheses())\n" +
                "T_keyword(=)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(new)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(def)\n" +
                "T_identifier(sort)\n" +
                "T_parentheses(()\n" +
                "T_identifier(p)\n" +
                "T_keyword(:)\n" +
                "T_parentheses(()\n" +
                "T_identifier(A)\n" +
                "T_delimiter(comma)\n" +
                "T_identifier(A)\n" +
                "T_parentheses())\n" +
                "T_keyword(=>)\n" +
                "T_identifier(Boolean)\n" +
                "T_parentheses())\n" +
                "T_keyword(=)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(util)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(Sorting)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(stableSort)\n" +
                "T_parentheses(()\n" +
                "T_identifier(x)\n" +
                "T_delimiter(comma)\n" +
                "T_identifier(p)\n" +
                "T_parentheses())\n" +
                "T_delimiter(comma)\n" +
                "T_identifier(x)\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(val)\n" +
                "T_identifier(x)\n" +
                "T_keyword(=)\n" +
                "T_identifier(Array)\n" +
                "T_parentheses(()\n" +
                "T_literal_integer(2)\n" +
                "T_delimiter(comma)\n" +
                "T_literal_integer(3)\n" +
                "T_delimiter(comma)\n" +
                "T_literal_integer(1)\n" +
                "T_delimiter(comma)\n" +
                "T_literal_integer(4)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"x = \")\n" +
                "T_identifier(+)\n" +
                "T_identifier(x)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(sort)\n" +
                "T_parentheses(()\n" +
                "T_parentheses(()\n" +
                "T_identifier(x)\n" +
                "T_keyword(:)\n" +
                "T_identifier(Int)\n" +
                "T_delimiter(comma)\n" +
                "T_identifier(y)\n" +
                "T_keyword(:)\n" +
                "T_identifier(Int)\n" +
                "T_parentheses())\n" +
                "T_keyword(=>)\n" +
                "T_identifier(x)\n" +
                "T_identifier(<)\n" +
                "T_identifier(y)\n" +
                "T_parentheses())\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test12() {
        String input = "/** Maps are easy to use in Scala. */\n" +
                "object Maps {\n" +
                "  val colors = Map(\"red\" -> 0xFF0000,\n" +
                "                   \"turquoise\" -> 0x00FFFF,\n" +
                "                   \"black\" -> 0x000000,\n" +
                "                   \"orange\" -> 0xFF8040,\n" +
                "                   \"brown\" -> 0x804000)\n" +
                "  def main(args: Array[String]) {\n" +
                "    for (name <- args) println(\n" +
                "      colors.get(name) match {\n" +
                "        case Some(code) =>\n" +
                "          name + \" has code: \" + code\n" +
                "        case None =>\n" +
                "          \"Unknown color: \" + name\n" +
                "      }\n" +
                "    )\n" +
                "  }\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_comment(/** Maps are easy to use in Scala. */)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(object)\n" +
                "T_identifier(Maps)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(val)\n" +
                "T_identifier(colors)\n" +
                "T_keyword(=)\n" +
                "T_identifier(Map)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"red\")\n" +
                "T_identifier(->)\n" +
                "T_literal_integer(0)\n" +
                "T_identifier(xFF0000)\n" +
                "T_delimiter(comma)\n" +
                "T_delimiter(new_line)\n" +
                "T_literal_string(\"turquoise\")\n" +
                "T_identifier(->)\n" +
                "T_literal_integer(0)\n" +
                "T_identifier(x00FFFF)\n" +
                "T_delimiter(comma)\n" +
                "T_delimiter(new_line)\n" +
                "T_literal_string(\"black\")\n" +
                "T_identifier(->)\n" +
                "T_literal_integer(0)\n" +
                "T_identifier(x000000)\n" +
                "T_delimiter(comma)\n" +
                "T_delimiter(new_line)\n" +
                "T_literal_string(\"orange\")\n" +
                "T_identifier(->)\n" +
                "T_literal_integer(0)\n" +
                "T_identifier(xFF8040)\n" +
                "T_delimiter(comma)\n" +
                "T_delimiter(new_line)\n" +
                "T_literal_string(\"brown\")\n" +
                "T_identifier(->)\n" +
                "T_literal_integer(0)\n" +
                "T_identifier(x804000)\n" +
                "T_parentheses())\n" +
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
                "T_keyword(for)\n" +
                "T_parentheses(()\n" +
                "T_identifier(name)\n" +
                "T_keyword(<-)\n" +
                "T_identifier(args)\n" +
                "T_parentheses())\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(colors)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(get)\n" +
                "T_parentheses(()\n" +
                "T_identifier(name)\n" +
                "T_parentheses())\n" +
                "T_keyword(match)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(case)\n" +
                "T_identifier(Some)\n" +
                "T_parentheses(()\n" +
                "T_identifier(code)\n" +
                "T_parentheses())\n" +
                "T_keyword(=>)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(name)\n" +
                "T_identifier(+)\n" +
                "T_literal_string(\" has code: \")\n" +
                "T_identifier(+)\n" +
                "T_identifier(code)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(case)\n" +
                "T_identifier(None)\n" +
                "T_keyword(=>)\n" +
                "T_delimiter(new_line)\n" +
                "T_literal_string(\"Unknown color: \")\n" +
                "T_identifier(+)\n" +
                "T_identifier(name)\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test13() {
        String input = "/** Basic command line parsing. */\n" +
                "object Main {\n" +
                "  var verbose = false\n" +
                "\n" +
                "  def main(args: Array[String]) {\n" +
                "    for (a <- args) a match {\n" +
                "      case \"-h\" | \"-help\"    =>\n" +
                "        println(\"Usage: scala Main [-help|-verbose]\")\n" +
                "      case \"-v\" | \"-verbose\" =>\n" +
                "        verbose = true\n" +
                "      case x =>\n" +
                "        println(\"Unknown option: '\" + x + \"'\")\n" +
                "    }\n" +
                "    if (verbose)\n" +
                "      println(\"How are you today?\")\n" +
                "  }\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_comment(/** Basic command line parsing. */)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(object)\n" +
                "T_identifier(Main)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(var)\n" +
                "T_identifier(verbose)\n" +
                "T_keyword(=)\n" +
                "T_literal_logical(false)\n" +
                "T_delimiter(new_line)\n" +
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
                "T_keyword(for)\n" +
                "T_parentheses(()\n" +
                "T_identifier(a)\n" +
                "T_keyword(<-)\n" +
                "T_identifier(args)\n" +
                "T_parentheses())\n" +
                "T_identifier(a)\n" +
                "T_keyword(match)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(case)\n" +
                "T_literal_string(\"-h\")\n" +
                "T_identifier(|)\n" +
                "T_literal_string(\"-help\")\n" +
                "T_keyword(=>)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"Usage: scala Main [-help|-verbose]\")\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(case)\n" +
                "T_literal_string(\"-v\")\n" +
                "T_identifier(|)\n" +
                "T_literal_string(\"-verbose\")\n" +
                "T_keyword(=>)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(verbose)\n" +
                "T_keyword(=)\n" +
                "T_literal_logical(true)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(case)\n" +
                "T_identifier(x)\n" +
                "T_keyword(=>)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"Unknown option: '\")\n" +
                "T_identifier(+)\n" +
                "T_identifier(x)\n" +
                "T_identifier(+)\n" +
                "T_literal_string(\"'\")\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(if)\n" +
                "T_parentheses(()\n" +
                "T_identifier(verbose)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"How are you today?\")\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test14() {
        String input = "/** Print prime numbers less than 100, very inefficiently */\n" +
                "object primes extends Application {\n" +
                "  def isPrime(n: Int) = (2 until n) forall (n % _ != 0)\n" +
                "  for (i <- 1 to 100 if isPrime(i)) println(i)\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_comment(/** Print prime numbers less than 100, very inefficiently */)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(object)\n" +
                "T_identifier(primes)\n" +
                "T_keyword(extends)\n" +
                "T_identifier(Application)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(def)\n" +
                "T_identifier(isPrime)\n" +
                "T_parentheses(()\n" +
                "T_identifier(n)\n" +
                "T_keyword(:)\n" +
                "T_identifier(Int)\n" +
                "T_parentheses())\n" +
                "T_keyword(=)\n" +
                "T_parentheses(()\n" +
                "T_literal_integer(2)\n" +
                "T_identifier(until)\n" +
                "T_identifier(n)\n" +
                "T_parentheses())\n" +
                "T_identifier(forall)\n" +
                "T_parentheses(()\n" +
                "T_identifier(n)\n" +
                "T_identifier(%)\n" +
                "T_keyword(_)\n" +
                "T_identifier(!=)\n" +
                "T_literal_integer(0)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(for)\n" +
                "T_parentheses(()\n" +
                "T_identifier(i)\n" +
                "T_keyword(<-)\n" +
                "T_literal_integer(1)\n" +
                "T_identifier(to)\n" +
                "T_literal_integer(100)\n" +
                "T_keyword(if)\n" +
                "T_identifier(isPrime)\n" +
                "T_parentheses(()\n" +
                "T_identifier(i)\n" +
                "T_parentheses())\n" +
                "T_parentheses())\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_identifier(i)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test15() {
        String input = "package examples\n" +
                "\n" +
                "/** Using Java varargs */\n" +
                "object varargs extends Application {\n" +
                "  val msg = java.text.MessageFormat.format(\n" +
                "    \"At {1,time} on {1,date}, there was {2} on planet {0}.\",\n" +
                "    \"Hoth\", new java.util.Date(), \"a disturbance in the Force\")\n" +
                "  println(\"Message=\"+msg)\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_keyword(package)\n" +
                "T_identifier(examples)\n" +
                "T_delimiter(new_line)\n" +
                "T_delimiter(new_line)\n" +
                "T_comment(/** Using Java varargs */)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(object)\n" +
                "T_identifier(varargs)\n" +
                "T_keyword(extends)\n" +
                "T_identifier(Application)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(val)\n" +
                "T_identifier(msg)\n" +
                "T_keyword(=)\n" +
                "T_identifier(java)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(text)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(MessageFormat)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(format)\n" +
                "T_parentheses(()\n" +
                "T_delimiter(new_line)\n" +
                "T_literal_string(\"At {1,time} on {1,date}, there was {2} on planet {0}.\")\n" +
                "T_delimiter(comma)\n" +
                "T_delimiter(new_line)\n" +
                "T_literal_string(\"Hoth\")\n" +
                "T_delimiter(comma)\n" +
                "T_keyword(new)\n" +
                "T_identifier(java)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(util)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(Date)\n" +
                "T_parentheses(()\n" +
                "T_parentheses())\n" +
                "T_delimiter(comma)\n" +
                "T_literal_string(\"a disturbance in the Force\")\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"Message=\")\n" +
                "T_identifier(+)\n" +
                "T_identifier(msg)\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }

    @Test
    public void test16() {
        String input = "object Main {\n" +
                "  def main(args: Array[String]) {\n" +
                "    try {\n" +
                "      val elems = args map Integer.parseInt\n" +
                "      println(\"The sum of my arguments is: \" + elems.foldRight(0) (_ + _))\n" +
                "    } catch {\n" +
                "      case e: NumberFormatException =>\n" +
                "        println(\"Usage: scala Main <n1> <n2> ... \")\n" +
                "    }\n" +
                "  }\n" +
                "}";
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);
        Token token;
        String answer = "";
        while ((token = lexicalAnalyzer.getNextToken()) != null) {
            answer = answer + token.toString() + "\n";
        }
        String res = "T_keyword(object)\n" +
                "T_identifier(Main)\n" +
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
                "T_keyword(try)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(val)\n" +
                "T_identifier(elems)\n" +
                "T_keyword(=)\n" +
                "T_identifier(args)\n" +
                "T_identifier(map)\n" +
                "T_identifier(Integer)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(parseInt)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"The sum of my arguments is: \")\n" +
                "T_identifier(+)\n" +
                "T_identifier(elems)\n" +
                "T_delimiter(dot)\n" +
                "T_identifier(foldRight)\n" +
                "T_parentheses(()\n" +
                "T_literal_integer(0)\n" +
                "T_parentheses())\n" +
                "T_parentheses(()\n" +
                "T_keyword(_)\n" +
                "T_identifier(+)\n" +
                "T_keyword(_)\n" +
                "T_parentheses())\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_keyword(catch)\n" +
                "T_parentheses({)\n" +
                "T_delimiter(new_line)\n" +
                "T_keyword(case)\n" +
                "T_identifier(e)\n" +
                "T_keyword(:)\n" +
                "T_identifier(NumberFormatException)\n" +
                "T_keyword(=>)\n" +
                "T_delimiter(new_line)\n" +
                "T_identifier(println)\n" +
                "T_parentheses(()\n" +
                "T_literal_string(\"Usage: scala Main <n1> <n2> ... \")\n" +
                "T_parentheses())\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_delimiter(new_line)\n" +
                "T_parentheses(})\n" +
                "T_end_of_file()\n";
        Assert.assertTrue(answer.equals(res));
    }
}
