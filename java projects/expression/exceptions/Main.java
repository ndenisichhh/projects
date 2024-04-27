package expression.exceptions;

import expression.ExpressionAndTriple;
import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        ExpressionAndTriple s = new ExpressionParser().parse("1000000*x*x*x*x*x/(x-1)");
        System.out.println("x      f");
        for (int x = 0; x <= 10; x++) {
            try {
                System.out.println(x + "      " + s.evaluate(x));
            } catch (IllegalArgumentException e) {
                System.out.println(x + "      " + e.getMessage());
            }
        }
    }
}
