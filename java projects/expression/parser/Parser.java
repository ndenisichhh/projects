package expression.parser;

public class Parser {
    private final char END = 0;
    private final char START = 1;
    protected CharSource source;
    protected char ch;

    public Parser(CharSource source) {
        this.source = source;
        take();
    }

    public Parser() {
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }
    protected boolean take(char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }
    protected boolean takeWh() {
        if (Character.isWhitespace(ch)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean test(char expected) {
        return ch == expected;
    }
    protected boolean between(char start, char end) {
        return start <= ch && ch <= end;
    }

    protected IllegalArgumentException error(String message){
        return source.error(message);
    }
    protected void checkEof() {
        if (!eof()) {
            throw error("Expected EOF, found " + "'" + take() + "'");
        }
    }
    protected boolean eof() {
        return ch == END;
    }
}
