package tokens;

public class ErrorToken extends Token {
    private String cause; // the cause of error
    private int symbol; // symbol where error starts

    public ErrorToken(String cause, int symbol) {
        this.cause = cause;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return String.format("T_error(cause=\"%s\", symbol=%d)", cause, symbol);
    }
}
