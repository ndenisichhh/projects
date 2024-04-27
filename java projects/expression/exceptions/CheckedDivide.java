package expression.exceptions;

import expression.BinaryOperations;
import expression.ExpressionAndTriple;

public class CheckedDivide extends BinaryOperations {
    public CheckedDivide(ExpressionAndTriple left, ExpressionAndTriple right) {
        super(left, right);
    }

    @Override
    protected int calc(int first, int second) {
        if (second == 0) throw new IllegalArgumentException("Division by zero with args: " + first + ", " + second);
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new IllegalArgumentException("Overflow in divide with args: " + first + ", " + second);
        }
        return first / second;
    }

    @Override
    protected char getOperation() {
        return '/';
    }
}
