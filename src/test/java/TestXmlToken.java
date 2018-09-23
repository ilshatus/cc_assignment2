import org.junit.Assert;
import org.junit.Test;
import tokens.ParenthesesToken;
import tokens.XmlToken;
import tokens.builders.Builder;
import tokens.enums.State;

public class TestXmlToken {
    @Test
    public void test1() {
        Builder builder = new XmlToken.Builder();
        String input = "<asdasd>  </asdasd>";
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
        Builder builder = new XmlToken.Builder();
        String input = "<asdasd> <asd> </asd>  </asdasd>";
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
        Builder builder = new XmlToken.Builder();
        String input = "<asdasd> <asd> <asd>";
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
        Builder builder = new XmlToken.Builder();
        String input = "asda ";
        State state = null;
        for(char c:input.toCharArray()){
            state = builder.addNextChar(c);
            if(state == State.NOT_MATCH)
                break;
        }
        Assert.assertFalse(state == State.MATCH);
    }
}
