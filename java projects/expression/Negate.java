package expression;

public class Negate extends UnaryOperations {
    public Negate(ExpressionAndTriple negative) {
        super(negative);
    }

    @Override
    protected int calc(int n) {
        return -n;
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
