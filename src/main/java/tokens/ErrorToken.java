package tokens;

public class ErrorToken extends Token {
    private String message;
    public ErrorToken(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("T_error(%s)", message);
    }
}
