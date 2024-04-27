package expression;

public class Not extends UnaryOperations{
    public Not(ExpressionAndTriple not) {
        super(not);
    }

    @Override
    protected int calc(int n) {
        return ~n;
    }

    @Override
    protected String getOperation() {
        return "~";
    }
}
