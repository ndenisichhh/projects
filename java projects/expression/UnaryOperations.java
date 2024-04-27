package expression;

import java.util.Objects;

public abstract class UnaryOperations implements ExpressionAndTriple {
    private final ExpressionAndTriple n;

    public UnaryOperations(ExpressionAndTriple n) {
        this.n = n;
    }
    protected abstract int calc(int n);
    protected abstract String getOperation();

    @Override
    public int evaluate(int x) {
        return calc(n.evaluate(x));
    }

    @Override
    public String toString() {
        return getOperation() + "(" +  n.toString() + ")";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calc(n.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return n.equals(((UnaryOperations) o).n);
    }

    @Override
    public int hashCode() {
        return Objects.hash(n, this.getClass());
    }
}
