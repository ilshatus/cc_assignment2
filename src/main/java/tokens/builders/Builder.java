package tokens.builders;

import tokens.Token;
import tokens.enums.State;

public interface Builder {
    State getState();
    void clear();
    State addNextChar(char ch);
    Token build();
}
