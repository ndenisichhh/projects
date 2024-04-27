package expression.exceptions;

import expression.ExpressionAndTriple;

import java.util.Objects;

public class CheckedNegate implements ExpressionAndTriple {
    private final ExpressionAndTriple negative;
    public CheckedNegate(ExpressionAndTriple negative) {
        this.negative = negative;
    }
    @Override
    public String toString() {
        return "-(" + negative + ")";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int result = negative.evaluate(x, y, z);
        if (result != Integer.MIN_VALUE) return -result;
        throw new IllegalArgumentException("Overflow in negate with arg: " + result);
    }

    @Override
    public int evaluate(int x) {
        int result = negative.evaluate(x);
        if (result != Integer.MIN_VALUE) return -result;
        throw new IllegalArgumentException("Overflow in negate with arg: " + result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return negative.equals(((CheckedNegate) o).negative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(negative, this.getClass());
    }
}
