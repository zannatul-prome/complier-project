import java.util.*;

public class Parser {

    List<Token> tokens;
    int pos = 0;

    Map<String, Integer> sym = new HashMap<>();
    IntermediateCodeGenerator icg = new IntermediateCodeGenerator();

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Token cur() {
        return tokens.get(pos);
    }

    void next() {
        pos++;
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

    int factor() {
        Token t = cur();

        if (t.type == TokenType.NUMBER) {
            next();
            return Integer.parseInt(t.value);
        }

        if (t.type == TokenType.IDENTIFIER) {
            next();
            return sym.getOrDefault(t.value, 0);
        }

        return 0;
    }

    void stmt() {
        String var = cur().value;
        next(); // id
        next(); // =

        int val = expr();
        sym.put(var, val);

        if (cur().type == TokenType.SEMICOLON) next();

        System.out.println(var + " = " + toBangla(val));
        icg.assign(var, "" + val);
    }

    String toBangla(int n) {
        String s = String.valueOf(n);
        String r = "";

        for (char c : s.toCharArray()) {
            switch (c) {
                case '0': r += "০"; break;
                case '1': r += "১"; break;
                case '2': r += "২"; break;
                case '3': r += "৩"; break;
                case '4': r += "৪"; break;
                case '5': r += "৫"; break;
                case '6': r += "৬"; break;
                case '7': r += "৭"; break;
                case '8': r += "৮"; break;
                case '9': r += "৯"; break;
            }
        }
        return r;
    }

    void parse() {
        while (cur().type != TokenType.EOF) {
            if (cur().type == TokenType.IDENTIFIER) {
                stmt();
            } else {
                next();
            }
        }

        icg.print();
    }
}