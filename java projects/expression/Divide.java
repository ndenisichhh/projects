package expression;

public class Divide extends BinaryOperations {
    public Divide(expression.ExpressionAndTriple n1, ExpressionAndTriple n2) {
        super(n1, n2);
    }

    @Override
    protected int calc(int first, int second) {
        return first / second;
    }

    @Override
    protected char getOperation() {
        return '/';
    }
}
