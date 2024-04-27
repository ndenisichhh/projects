package expression.exceptions;

import expression.BinaryOperations;
import expression.ExpressionAndTriple;

public class CheckedMultiply extends BinaryOperations {
    public CheckedMultiply(ExpressionAndTriple left, ExpressionAndTriple right) {
        super(left, right);
    }

    @Override
    protected int calc(int first, int second) {
        if (first == 0 || second == 0) return 0;
        if (first > 0) {
            if (second > 0 && first > Integer.MAX_VALUE / second ||
                second <= 0 && second < Integer.MIN_VALUE / first) {
                    throw new IllegalArgumentException("Overflow in multiply with args: " + first + ", " + second);
            }
        } else {
            if (second > 0 && first < Integer.MIN_VALUE / second ||
                second <= 0 && second < Integer.MAX_VALUE / first) {
                    throw new IllegalArgumentException("Overflow in multiply with args: " + first + ", " + second);
            }
        }
        return first * second;
    }

    @Override
    protected char getOperation() {
        return '*';
    }
}
