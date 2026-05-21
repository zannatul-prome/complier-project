import java.util.*;

public class Parser {

    static class ExprResult {
        int value;
        String place;

        ExprResult(int value, String place) {
            this.value = value;
            this.place = place;
        }
    }

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

    // Bangla number → int
    int toInt(String s) {
        return Integer.parseInt(
                s.replace('০', '0')
                 .replace('১', '1')
                 .replace('২', '2')
                 .replace('৩', '3')
                 .replace('৪', '4')
                 .replace('৫', '5')
                 .replace('৬', '6')
                 .replace('৭', '7')
                 .replace('৮', '8')
                 .replace('৯', '9')
        );
    }

    String toBengaliNumber(int num) {
        String englishNum = String.valueOf(num);
        StringBuilder result = new StringBuilder();

        for (char c : englishNum.toCharArray()) {
            switch (c) {
                case '0': result.append('০'); break;
                case '1': result.append('১'); break;
                case '2': result.append('২'); break;
                case '3': result.append('৩'); break;
                case '4': result.append('৪'); break;
                case '5': result.append('৫'); break;
                case '6': result.append('৬'); break;
                case '7': result.append('৭'); break;
                case '8': result.append('৮'); break;
                case '9': result.append('৯'); break;
                case '-': result.append('-'); break;
            }
        }

        return result.toString();
    }

    // FACTOR
    ExprResult factor() {
        Token t = cur();

        if (t.type == TokenType.NUMBER) {
            next();
            return new ExprResult(toInt(t.value), String.valueOf(toInt(t.value)));
        }

        if (t.type == TokenType.IDENTIFIER) {
            next();
            int val = sym.getOrDefault(t.value, 0);
            return new ExprResult(val, t.value);
        }

        if (t.type == TokenType.LEFT_PAREN) {
            next();
            ExprResult res = expr();
            if (cur().type == TokenType.RIGHT_PAREN) next();
            return res;
        }

        return new ExprResult(0, "0");
    }

    // TERM
    ExprResult term() {
        ExprResult left = factor();

        while (cur().type == TokenType.MULTIPLY) {
            next();
            ExprResult right = factor();

            String t = icg.newTemp();
            icg.binary(t, left.place, "*", right.place);

            int val = left.value * right.value;
            left = new ExprResult(val, t);
        }

        return left;
    }

    // EXPR
    ExprResult expr() {
        ExprResult left = term();

        while (cur().type == TokenType.PLUS || cur().type == TokenType.MINUS) {
            Token opToken = cur();
            next();
            ExprResult right = term();

            String t = icg.newTemp();
            String op = opToken.type == TokenType.PLUS ? "+" : "-";
            icg.binary(t, left.place, op, right.place);

            int val;
            if (opToken.type == TokenType.PLUS) {
                val = left.value + right.value;
            } else {
                val = left.value - right.value;
            }
            left = new ExprResult(val, t);
        }

        return left;
    }

    // STATEMENT
    void stmt() {
        if (cur().type != TokenType.IDENTIFIER) {
            return;
        }
        String var = cur().value;
        next(); // var

        if (cur().type == TokenType.ASSIGN) {
            next(); // =
        }

        ExprResult res = expr();

        sym.put(var, res.value);

        if (cur().type == TokenType.SEMICOLON) next();

        System.out.println(var + " = " + toBengaliNumber(res.value));

        icg.assign(var, res.place);
    }

    // IF-ELSE
    void parseIfElse() {
        next(); // consume IF ('যদি')
        if (cur().type == TokenType.LEFT_PAREN) next(); // consume '('

        ExprResult left = expr();

        String op = "";
        if (cur().type == TokenType.GT) {
            op = ">";
            next();
        } else if (cur().type == TokenType.LT) {
            op = "<";
            next();
        } else if (cur().type == TokenType.EQ) {
            op = "==";
            next();
        }

        ExprResult right = expr();

        if (cur().type == TokenType.RIGHT_PAREN) next(); // consume ')'
        if (cur().type == TokenType.THEN) next(); // consume 'তাহলে'

        boolean condition = false;
        if (op.equals(">")) {
            condition = left.value > right.value;
        } else if (op.equals("<")) {
            condition = left.value < right.value;
        } else if (op.equals("==")) {
            condition = left.value == right.value;
        }

        if (condition) {
            System.out.println("শর্ত সত্য → IF ব্লক চলবে");
        } else {
            System.out.println("শর্ত মিথ্যা → ELSE ব্লক চলবে");
        }

        // Generate intermediate code for comparison
        String condTemp = icg.newTemp();
        icg.binary(condTemp, left.place, op, right.place);

        if (condition) {
            // Execute the statement inside IF block
            parseStatement();

            if (cur().type == TokenType.ELSE) {
                next(); // consume 'নাহলে'
                skipStatement(); // skip the statement inside ELSE block
            }
        } else {
            // Skip the statement inside IF block
            skipStatement();

            if (cur().type == TokenType.ELSE) {
                next(); // consume 'নাহলে'
                parseStatement(); // execute the statement inside ELSE block
            }
        }
    }

    void parseStatement() {
        if (cur().type == TokenType.IF) {
            parseIfElse();
        } else if (cur().type == TokenType.IDENTIFIER) {
            stmt();
        } else {
            next();
        }
    }

    void skipStatement() {
        if (cur().type == TokenType.IF) {
            next(); // consume 'যদি'
            while (cur().type != TokenType.SEMICOLON && cur().type != TokenType.EOF) {
                if (cur().type == TokenType.IF) {
                    skipStatement();
                } else {
                    next();
                }
            }
            if (cur().type == TokenType.SEMICOLON) next();
        } else {
            while (cur().type != TokenType.SEMICOLON && cur().type != TokenType.EOF) {
                next();
            }
            if (cur().type == TokenType.SEMICOLON) next();
        }
    }

    // MAIN PARSE
    void parse() {
        System.out.println("\nফলাফল:");
        System.out.println("========");

        while (cur().type != TokenType.EOF) {
            parseStatement();
        }

        icg.print();
    }
}