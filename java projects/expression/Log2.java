package expression;

public class Log2 extends UnaryOperations {
    public Log2(ExpressionAndTriple log2) {
        super(log2);
    }

    @Override
    protected int calc(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid arg: " + n);
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    @Override
    protected String getOperation() {
        return "log2";
    }
}
