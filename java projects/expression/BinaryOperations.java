package expression;

import java.util.Objects;

public abstract class BinaryOperations implements ExpressionAndTriple {
    private final ExpressionAndTriple n1;
    private final ExpressionAndTriple n2;

    public BinaryOperations(ExpressionAndTriple n1, ExpressionAndTriple n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    protected abstract int calc(int first, int second);
    protected abstract char getOperation();

    @Override
    public int evaluate(int x) {
        return calc(n1.evaluate(x), n2.evaluate(x));
    }

    @Override
    public String toString() {
        return "(" + n1 + " " + getOperation() + " " + n2 + ")";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calc(n1.evaluate(x, y, z), n2.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return n1.equals(((BinaryOperations) o).n1) && n2.equals(((BinaryOperations) o).n2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(n1, n2, this.getClass());
    }
}
