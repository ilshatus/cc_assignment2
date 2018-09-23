import org.junit.Assert;
import org.junit.Test;
import tokens.ParenthesesToken;
import tokens.PlainIdentifierToken;
import tokens.builders.Builder;
import tokens.enums.State;

public class TestPlainIdentifierToken {

    @Test
    public void test1() {
        Builder builder = new PlainIdentifierToken.Builder();
        String input = "_123";
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
        Builder builder = new PlainIdentifierToken.Builder();
        String input = "asd362";
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
        Builder builder = new PlainIdentifierToken.Builder();
        String input = "sada__asdah52";
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
        Builder builder = new PlainIdentifierToken.Builder();
        String input = "{{";
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
        Builder builder = new PlainIdentifierToken.Builder();
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
