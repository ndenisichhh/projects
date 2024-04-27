package expression.exceptions;

import expression.BinaryOperations;
import expression.ExpressionAndTriple;

public class CheckedSubtract extends BinaryOperations {
    public CheckedSubtract(ExpressionAndTriple left, ExpressionAndTriple right) {
        super(left, right);
    }

    @Override
    protected int calc(int first, int second) {
        if (second < 0 && first > (Integer.MAX_VALUE + second) ||
            second > 0 && first < (Integer.MIN_VALUE + second))
                throw new IllegalArgumentException("Overflow in subtract with args: " + first + ", " + second);
        return first - second;
    }

    @Override
    protected char getOperation() {
        return '-';
    }
}
