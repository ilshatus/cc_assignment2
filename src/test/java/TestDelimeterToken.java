import org.junit.Assert;
import org.junit.Test;
import tokens.DelimiterToken;
import tokens.builders.Builder;
import tokens.enums.State;

public class TestDelimeterToken {
    @Test
    public void test1() {
        Builder builder = new DelimiterToken.Builder();
        String input = ";";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
        }
        Assert.assertTrue(state == State.MATCH);

    }

    @Test
    public void test2() {
        Builder builder = new DelimiterToken.Builder();
        String input = "\n";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
        }
        Assert.assertTrue(state == State.MATCH);

    }

    @Test
    public void test3() {
        Builder builder = new DelimiterToken.Builder();
        String input = ".";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
        }
        Assert.assertTrue(state == State.MATCH);

    }

    @Test
    public void test4() {
        Builder builder = new DelimiterToken.Builder();
        String input = ",";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
        }
        Assert.assertTrue(state == State.MATCH);

    }

    @Test
    public void test5() {
        Builder builder = new DelimiterToken.Builder();
        String input = "a";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
        }
        Assert.assertFalse(state == State.MATCH);
    }

    @Test
    public void test6() {
        Builder builder = new DelimiterToken.Builder();
        String input = ";a";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
        }
        Assert.assertFalse(state == State.MATCH);
    }


}
