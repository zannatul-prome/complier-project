import java.util.*;

public class Parser {

    List<Token> tokens;
    int pos = 0;

    Map<String, Integer> sym = new HashMap<>();
    IntermediateCodeGenerator icg = new IntermediateCodeGenerator();

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Token cur() { return tokens.get(pos); }
    void next() { pos++; }

    int toInt(String s) {
        return Integer.parseInt(s.replace('০','0')
                                 .replace('১','1')
                                 .replace('২','2')
                                 .replace('৩','3')
                                 .replace('৪','4')
                                 .replace('৫','5')
                                 .replace('৬','6')
                                 .replace('৭','7')
                                 .replace('৮','8')
                                 .replace('৯','9'));
    }

    int factor() {
        Token t = cur();

        if (t.type == TokenType.NUMBER) {
            next();
            return toInt(t.value);
        }

        if (t.type == TokenType.IDENTIFIER) {
            next();
            return sym.getOrDefault(t.value, 0);
        }

        return 0;
    }

    int term() {
        int left = factor();

        while (cur().type == TokenType.MULTIPLY) {
            next();
            int right = factor();

            String t = icg.newTemp();
            icg.binary(t, "" + left, "*", "" + right);

            left = left * right;
        }

        return left;
    }

    int expr() {
        int left = term();

        while (cur().type == TokenType.PLUS) {
            next();
            int right = term();

            String t = icg.newTemp();
            icg.binary(t, "" + left, "+", "" + right);

            left = left + right;
        }

        return left;
    }

    void stmt() {
        String var = cur().value;
        next(); next();

        int val = expr();
        sym.put(var, val);

        if (cur().type == TokenType.SEMICOLON) next();

        System.out.println(var + " = " + val);
        icg.assign(var, "" + val);
    }

    void parse() {

        // ক = 10
        stmt();

        // IF
        next(); // যদি

        next(); // (
        int left = expr();

        next(); // >
        int right = expr();

        String t = icg.newTemp();
        icg.binary(t, "" + left, ">", "" + right);

        boolean result = left > right;

        next(); // )
        next(); // তাহলে

        if (result) {
            stmt(); // IF
            while (cur().type != TokenType.ELSE) next();
            next(); // skip else
        } else {
            while (cur().type != TokenType.ELSE) next();
            next();
            stmt(); // ELSE
        }

        // গ = খ * 2
        stmt();

        icg.print();
    }
}