package tokens;

public class ErrorToken extends Token {
    private String message;
    private int line;

    public ErrorToken(String message, int line) {
        this.message = message;
        this.line = line;
    }

    @Override
    public String toString() {
        return String.format("T_error(message=\"%s\", line=%d)", message, line);
    }
}
