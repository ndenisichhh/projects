package expression.exceptions;

import expression.BinaryOperations;
import expression.ExpressionAndTriple;

public class CheckedAdd extends BinaryOperations {
    public CheckedAdd(ExpressionAndTriple left, ExpressionAndTriple right) {
        super(left, right);
    }

    @Override
    protected int calc(int first, int second) {
        if (first > 0 && second > 0 && (first+second) < 0 ||
            first < 0 && second < 0 && (first + second >= 0)){
                throw new IllegalArgumentException("Overflow in add with args: " + first + ", " + second);}
        return first + second;
    }

    @Override
    protected char getOperation() {
        return '+';
    }
}
