package tokens.builders;

import tokens.Token;
import tokens.enums.State;

public interface Builder {
    State addNextChar(char ch);
    Token build();
    void clear();
}
