import java.util.*;

public class Parser {

    static List<Token> tokens;
    static int current = 0;

    public static void parse(List<Token> tokenList) {
        tokens = tokenList;
        current = 0;

        while (current < tokens.size()) {
            statement();
        }
    }

    // statement → declaration | assignment
    static void statement() {

        if (match("TYPE")) {
            declaration();
        }

        else if (check("IDENTIFIER")) {
            assignment();
        }

        else {
            System.out.println("Syntax Error near -> " + peek().value);
            current++;
        }
    }

    // declaration → TYPE ID ;
    static void declaration() {

        String declaredType = previous().value;

        if (match("IDENTIFIER")) {
            String varName = previous().value;

            if (match("SEMICOLON")) {
                SymbolTable.insert(varName, declaredType);
            } else {
                System.out.println("Missing ';' after declaration");
            }

        } else {
            System.out.println("Identifier expected after type");
        }
    }

    // assignment → ID = expression ;
    static void assignment() {

        match("IDENTIFIER");
        String varName = previous().value;

        if (!SymbolTable.exists(varName)) {
            System.out.println("Undeclared Variable -> " + varName);
        }

        String leftType = SymbolTable.getType(varName);

        if (!match("ASSIGN")) {
            System.out.println("Missing '=' in assignment");
            return;
        }

        String rightType = expression();

        if (leftType != null && rightType != null) {
            if (leftType.equals("সংখ্যা") && rightType.equals("FLOAT")) {
                System.out.println("Type Error: float assigned to int -> " + varName);
            }
        }

        if (!match("SEMICOLON")) {
            System.out.println("Missing ';' after assignment");
        } else {
            System.out.println("Valid Assignment -> " + varName);
        }
    }

    // expression → term ((+ | -) term)*
    static String expression() {
        String type = term();

        while (match("PLUS") || match("MINUS")) {
            String nextType = term();

            if (type.equals("FLOAT") || nextType.equals("FLOAT")) {
                type = "FLOAT";
            }
        }

        return type;
    }

    // term → factor ((* | /) factor)*
    static String term() {
        String type = factor();

        while (match("MULTIPLY") || match("DIVIDE")) {
            String nextType = factor();

            if (type.equals("FLOAT") || nextType.equals("FLOAT")) {
                type = "FLOAT";
            }
        }

        return type;
    }

    // factor → NUMBER | FLOAT | ID | (expression)
    static String factor() {

        if (match("NUMBER")) {
            return "NUMBER";
        }

        if (match("FLOAT")) {
            return "FLOAT";
        }

        if (match("IDENTIFIER")) {
            String varName = previous().value;

            if (!SymbolTable.exists(varName)) {
                System.out.println("Undeclared Variable Used -> " + varName);
                return "UNKNOWN";
            }

            String varType = SymbolTable.getType(varName);

            if (varType.equals("দশমিক")) {
                return "FLOAT";
            }

            return "NUMBER";
        }

        if (match("LEFT_PAREN")) {
            String type = expression();

            if (!match("RIGHT_PAREN")) {
                System.out.println("Missing ')' after expression");
            }

            return type;
        }

        System.out.println("Invalid expression near -> " + peek().value);
        return "UNKNOWN";
    }

    // helper methods

    static boolean match(String type) {
        if (check(type)) {
            current++;
            return true;
        }
        return false;
    }

    static boolean check(String type) {
        if (isAtEnd()) return false;
        return peek().type.equals(type);
    }

    static Token peek() {
        return tokens.get(current);
    }

    static Token previous() {
        return tokens.get(current - 1);
    }

    static boolean isAtEnd() {
        return current >= tokens.size();
    }
}