import org.junit.Assert;
import org.junit.Test;
import tokens.MultilineCommentToken;
import tokens.SimpleCommentToken;
import tokens.builders.Builder;
import tokens.enums.State;

public class TestCommentToken {
    @Test
    public void test1() {
        Builder builder = new SimpleCommentToken.Builder();
        String input = "//asdasd";
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
        Builder builder = new SimpleCommentToken.Builder();
        String input = "//asd asd bla mngj  ;; ; x = a";
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
        Builder builder = new SimpleCommentToken.Builder();
        String input = "asd //asd asd bla mngj  ;; ; x = a ";
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
        Builder builder = new SimpleCommentToken.Builder();
        String input = "//asd asd bla mngj  ;; ; x = a \n asas here ";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    @Test
    public void test5() {
        Builder builder = new MultilineCommentToken.Builder();
        String input = "/* //asd asd bla mngj  ;; ; x = a \n asas here */";
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
        Builder builder = new MultilineCommentToken.Builder();
        String input = "/* / dasf;saf */";
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
        Builder builder = new MultilineCommentToken.Builder();
        String input = "asd /* / dasf;saf */";
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
        Builder builder = new MultilineCommentToken.Builder();
        String input = "/*  /* / dasf;saf */";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    @Test
    public void test9() {
        Builder builder = new MultilineCommentToken.Builder();
        String input = "";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }
}
