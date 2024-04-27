package expression;

public class Pow2 extends UnaryOperations {
    public Pow2(ExpressionAndTriple pow2) {
        super(pow2);
    }

    @Override
    protected int calc(int n) {
        if (n >= 31)
            throw new IllegalArgumentException("Overflow in pow2 with arg: " + n);
        if (n < 0) throw new IllegalArgumentException("Invalid pow2 with arg: " + n);
        return 1 << n;
    }

    @Override
    protected String getOperation() {
        return "pow2";
    }
}
