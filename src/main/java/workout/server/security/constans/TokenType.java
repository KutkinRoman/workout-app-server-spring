package workout.server.security.constans;

public enum TokenType {

    BEARER("Bearer ");

    private final String header;

    TokenType (String header) {
        this.header = header;
    }

    public String getHeader () {
        return header;
    }
}
