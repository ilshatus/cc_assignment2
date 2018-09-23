package tokens;

import tokens.builders.SimpleRegexBuilder;
import tokens.enums.State;

import java.util.regex.Pattern;

public class XmlToken extends Token {
    private String lexeme;

    private XmlToken(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("T_xml(%s)", lexeme);
    }

    public static class Builder extends SimpleRegexBuilder {
        private static final String regexp = "^<(.|\\n)+>(.|\\n)*<(.|\\n)+>$";

        public Builder() {
            super();
            super.p = Pattern.compile(regexp, Pattern.MULTILINE);
        }

        @Override
        public XmlToken build() {
            if (state.equals(State.MATCH))
                return new XmlToken(lexeme.toString());
            return null;
        }
    }
}
