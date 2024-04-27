package expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int test = new Add(
                new expression.Subtract(
                        new Multiply(
                                new expression.Variable("x"), new expression.Variable("x")
                        ), new Multiply(
                        new expression.Const(2), new expression.Variable("x")
                )
                ), new expression.Const(1)
        ).evaluate(11);
        System.out.println(test);
        System.out.println(new Multiply(new expression.Const(2), new expression.Variable("x"))
                .equals(new Multiply(new expression.Const(2), new expression.Variable("x"))));
        System.out.println(new Multiply(new expression.Const(2), new expression.Variable("x")).hashCode());
        System.out.println(new Multiply(new Const(2), new expression.Variable("x")).hashCode());

        ExpressionAndTriple vx = new expression.Variable("x");
        ExpressionAndTriple vy = new expression.Variable("y");
        ExpressionAndTriple vz = new Variable("z");
        String test1 = new Divide(new Divide(vz, new Subtract(vy, vx)), vx).toString();
        System.out.println(test1);
    }
}
