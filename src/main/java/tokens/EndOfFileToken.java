package tokens;

public class EndOfFileToken extends Token {
    public EndOfFileToken() {}

    @Override
    public String toString() {
        return "T_end_of_file";
    }
}
