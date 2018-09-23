import org.junit.Assert;
import org.junit.Test;
import tokens.builders.Builder;
import tokens.enums.State;
import tokens.literals.*;

public class TestLiteralTokens {

    //CHARACTER LITERAL
    @Test
    public void test1() {
        Builder builder = new CharacterLiteralToken.Builder();
        String input = "'c'";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test2() {
        Builder builder = new CharacterLiteralToken.Builder();
        String input = "'\u1345'";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test3() {
        Builder builder = new CharacterLiteralToken.Builder();
        String input = "\u1345'";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    @Test
    public void test4() {
        Builder builder = new CharacterLiteralToken.Builder();
        String input = "a";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    //LOGICAL LITERAL

    @Test
    public void test5() {
        Builder builder = new LogicalLiteralToken.Builder();
        String input = "true";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test6() {
        Builder builder = new LogicalLiteralToken.Builder();
        String input = "false";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test7() {
        Builder builder = new LogicalLiteralToken.Builder();
        String input = "fals";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    @Test
    public void test8() {
        Builder builder = new LogicalLiteralToken.Builder();
        String input = "False";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    // MULTILINE STRING LITERAL

    @Test
    public void test9() {
        Builder builder = new MultilineStringLiteralToken.Builder();
        String input = "\"\"\" asd asd as  \"\"\"";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test10() {
        Builder builder = new MultilineStringLiteralToken.Builder();
        String input = "\"\"\" \nasd \n  \n asd as\n   \"\"\"";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test11() {
        Builder builder = new MultilineStringLiteralToken.Builder();
        String input = "\"\"\" \nasd \n  \n asd as\n   \"\"";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    //NUMERIC LITERAL

    @Test
    public void test12() {
        Builder builder = new NumericLiteralToken.Builder();
        String input = "+1235";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test13() {
        Builder builder = new NumericLiteralToken.Builder();
        String input = "-1235";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test14() {
        Builder builder = new NumericLiteralToken.Builder();
        String input = "-1235.895100";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test15() {
        Builder builder = new NumericLiteralToken.Builder();
        String input = "-1235e895100";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test16() {
        Builder builder = new NumericLiteralToken.Builder();
        String input = "-1235e.895100";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    @Test
    public void test17() {
        Builder builder = new NumericLiteralToken.Builder();
        String input = "";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    //STRING LITERAL

    @Test
    public void test18() {
        Builder builder = new StringLiteralToken.Builder();
        String input = "\" asdas da\"";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test19() {
        Builder builder = new StringLiteralToken.Builder();
        String input = "\" asdas 05 *  da\"";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test20() {
        Builder builder = new StringLiteralToken.Builder();
        String input = "\"\"";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test21() {
        Builder builder = new StringLiteralToken.Builder();
        String input = "";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    //SYMBOL LITERAL

    @Test
    public void test22() {
        Builder builder = new SymbolLiteralToken.Builder();
        String input = "'asdasdassasd";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test23() {
        Builder builder = new SymbolLiteralToken.Builder();
        String input = "\'asdasdas sasd\'";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

}
