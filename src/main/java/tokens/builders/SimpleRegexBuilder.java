package tokens.builders;

import tokens.enums.State;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SimpleRegexBuilder implements Builder {
    protected Pattern p;
    protected StringBuilder lexeme;
    protected State state;
    private int length;

    protected SimpleRegexBuilder() {
        lexeme = new StringBuilder();
        length = 0;
        state = State.PARTIALLY_MATCH;
    }

    @Override
    public void clear() {
        lexeme = new StringBuilder();
        length = 0;
        state = State.PARTIALLY_MATCH;
    }

    private void appendLexeme(char ch) {
        lexeme.append(ch);
        length++;
    }

    private void cutLexemeEnd() {
        length--;
        lexeme.deleteCharAt(length);
    }

    @Override
    public State addNextChar(char ch) {
        appendLexeme(ch);
        Matcher matcher = p.matcher(lexeme);
        if (state.equals(State.MATCH)) {
            if (matcher.matches()) {
                return state;
            }
            if (matcher.hitEnd()) {
                return state = State.PARTIALLY_MATCH;
            }
            cutLexemeEnd();
            return State.NOT_MATCH;
        }
        if (matcher.matches()) {
            return state = State.MATCH;
        }
        if (matcher.hitEnd()) {
            return state = State.PARTIALLY_MATCH;
        }
        cutLexemeEnd();
        return State.NOT_MATCH;

    }
}
