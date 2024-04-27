package expression;

import java.util.Objects;

public class Variable implements ExpressionAndTriple {
    private final String var;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (var) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return Objects.equals(var, o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(var);
    }
}
