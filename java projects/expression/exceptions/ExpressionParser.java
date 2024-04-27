package expression.exceptions;

import expression.*;
import expression.parser.CharSource;
import expression.parser.Parser;
import expression.parser.StringCharSource;
import expression.parser.TripleParser;

import java.util.Map;

public final class ExpressionParser extends Parser implements TripleParser {
    public ExpressionParser() {
        super();
    }
    public ExpressionParser(CharSource source) {
        super(source);
    }

    private final Map<Character, Integer> orders = Map.of(
            '|', 1,
            '^', 2,
            '&', 3,
            '-', 4,
            '+', 4,
            '*', 5,
            '/', 5
    );

    private ExpressionAndTriple create(ExpressionAndTriple left, ExpressionAndTriple right, char operation) {
        return switch (operation) {
            case '+' -> new CheckedAdd(left, right);
            case '-' -> new CheckedSubtract(left, right);
            case '*' -> new CheckedMultiply(left, right);
            case '/' -> new CheckedDivide(left, right);
            case '&' -> new And(left, right);
            case '^' -> new Xor(left, right);
            case '|' -> new Or(left, right);
            default -> throw error("Invalid operation");
        };
    }
    private char nextOperation() {
        for (char c: orders.keySet()) {
            if (test(c)) return c;
        }
        throw error("Invalid expression, found '" + take() + "'");
    }
    public ExpressionAndTriple parse(final StringCharSource source) {
        return new ExpressionParser(source).parseExpressions();
    }
    public ExpressionAndTriple parse(final String string) {
//        System.err.println(string);
        return parse(new StringCharSource(string));
    }
    private ExpressionAndTriple parseExpressions() {
        skipWhiteSpace();
        final ExpressionAndTriple result = parseExpression(parsePart(), -1, -1);
        checkEof();
        return result;
    }
    private ExpressionAndTriple parseExpression(ExpressionAndTriple left, int firstP, int secondP) throws IllegalArgumentException {
        skipWhiteSpace();
        if (eof() || ch == ')') return left;
        char operation = nextOperation();
        int thisP = orders.get(operation);
        if (thisP <= firstP || thisP <= secondP) return left;
        take();
        ExpressionAndTriple right = parseExpression(parsePart(), secondP, thisP);
        return parseExpression(create(left, right, operation), firstP, secondP);
    }
    private ExpressionAndTriple parseInner() {
        ExpressionAndTriple result = parseExpression(parsePart(), -1, -1);
        skipWhiteSpace();
        if (take(')')) return result;
        throw error("Invalid inner");
    }
    private ExpressionAndTriple parsePart() {
        skipWhiteSpace();
        if (take('(')) {
            skipWhiteSpace();
            return parseInner();
        } else if (take('-')) {
            if (takeWh() || test('(')) return new CheckedNegate(parsePart());
            if (take('-')) return parseValue(false);
            return parseValue(true);
        } else if (take('~')) {
            return new Not(parsePart());
        }  else if (take('p')) {
            expect("ow2");
            if (takeWh() || test('(')) return new Pow2(parsePart());
            throw error("Invalid pow2");
        } else if (take('l')) {
            expect("og2");
            if (takeWh() || test('(')) return new Log2(parsePart());
            throw error("Invalid log2");
        }
        return parseValue(false);
    }
    private ExpressionAndTriple parseValue(boolean isNegative) {
        skipWhiteSpace();
        if (between('0', '9')){
            return parseInteger(isNegative);
        } else if (between('x', 'z')) {
            return parseVariable(isNegative);
        }
        throw error("Unsupported value: " + take());
    }
    private ExpressionAndTriple parseVariable(boolean isNegative) {
        ExpressionAndTriple variable = new Variable(String.valueOf(take()));
        return isNegative ? new CheckedNegate(variable) : variable;
    }
    private ExpressionAndTriple parseInteger(boolean isNegative) {
        final StringBuilder number = new StringBuilder();
        if (isNegative) number.append('-');
        if (take('0')){
            return new Const(0);
        }
        while (between('0', '9')) {
            number.append(take());
        }
        try {
            return new Const(Integer.parseInt(number.toString()));
        } catch (NumberFormatException e) {
            throw error("Invalid number: " + number);
        }
    }
    private void skipWhiteSpace() {
        while(takeWh()){}
    }
}