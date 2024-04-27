package expression;

public class Or extends BinaryOperations{
    public Or(expression.ExpressionAndTriple n1, ExpressionAndTriple n2) {
        super(n1, n2);
    }

    @Override
    protected int calc(int fisrst, int seconf) {
        return fisrst | seconf;
    }

    @Override
    protected char getOperation() {
        return '|';
    }
}
