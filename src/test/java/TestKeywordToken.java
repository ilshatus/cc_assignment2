import org.junit.Assert;
import org.junit.Test;
import tokens.KeywordToken;
import tokens.MultilineCommentToken;
import tokens.builders.Builder;
import tokens.enums.State;

public class TestKeywordToken {

    @Test
    public void test1() {
        Builder builder = new KeywordToken.Builder();
        String input = "else";
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
        Builder builder = new KeywordToken.Builder();
        String input = "if";
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
        Builder builder = new KeywordToken.Builder();
        String input = "with";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertTrue(state == State.MATCH);
    }

    @Test
    public void test4() {
        Builder builder = new KeywordToken.Builder();
        String input = "Case";
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
        Builder builder = new KeywordToken.Builder();
        String input = "abc";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }

    @Test
    public void test6() {
        Builder builder = new KeywordToken.Builder();
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
