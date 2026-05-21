import java.util.*;

public class Parser {

    private final List<Token> tokens;
    private int pos;
    private final Map<String, Integer> variables;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
        this.variables = new HashMap<>();
    }

    Token current() {
        return tokens.get(pos);
    }

    void advance() {
        pos++;
    }

    boolean parseCondition() {
        int left = parseExpression();
        boolean result = false;

        if (current().type == TokenType.GT) {
            advance();
            int right = parseExpression();
            result = (left > right);
        } else if (current().type == TokenType.LT) {
            advance();
            int right = parseExpression();
            result = (left < right);
        } else if (current().type == TokenType.EQ) {
            advance();
            int right = parseExpression();
            result = (left == right);
        }

        return result;
    }

    int parseExpression() {
        int result = parseTerm();

        while (current().type == TokenType.PLUS || current().type == TokenType.MINUS) {
            if (current().type == TokenType.PLUS) {
                advance();
                int right = parseTerm();
                result += right;
            } else if (current().type == TokenType.MINUS) {
                advance();
                int right = parseTerm();
                result -= right;
            }
        }

        return result;
    }

    int parseTerm() {
        int result = parseFactor();

        while (current().type == TokenType.MULTIPLY) {
            advance();
            int right = parseFactor();
            result *= right;
        }

        return result;
    }

    int parseFactor() {
        Token token = current();

        if (token.type == TokenType.NUMBER) {
            advance();
            return Integer.parseInt(token.value);
        } else if (token.type == TokenType.IDENTIFIER) {
            advance();
            String varName = token.value;
            return variables.getOrDefault(varName, 0);
        } else if (token.type == TokenType.LEFT_PAREN) {
            advance();
            int result = parseExpression();
            if (current().type == TokenType.RIGHT_PAREN) {
                advance();
            }
            return result;
        }

        return 0;
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
            }
        }

        return result.toString();
    }

    void parse() {
        System.out.println("\nফলাফল:");
        System.out.println("========");

        while (current().type != TokenType.EOF) {
            if (current().type == TokenType.IF) {
                parseIfElse();
            } else {
                parseStatement();
            }
        }

        System.out.println("\n✔ প্রোগ্রাম সঠিক!");
    }

    void parseIfElse() {
        advance();

        if (current().type == TokenType.LEFT_PAREN) {
            advance();
            boolean condition = parseCondition();

            if (current().type == TokenType.RIGHT_PAREN) {
                advance();
            }

            if (current().type == TokenType.THEN) {
                advance();
            }

            if (condition) {
                System.out.println("শর্ত সত্য → IF ব্লক চলবে");
                while (current().type != TokenType.ELSE && current().type != TokenType.EOF) {
                    if (current().type == TokenType.IDENTIFIER) {
                        parseStatement();
                    } else {
                        break;
                    }
                }
            } else {
                System.out.println("শর্ত মিথ্যা → ELSE ব্লক চলবে");
                while (current().type != TokenType.ELSE && current().type != TokenType.EOF) {
                    advance();
                }

                if (current().type == TokenType.ELSE) {
                    advance();
                    while (current().type != TokenType.EOF) {
                        if (current().type == TokenType.IDENTIFIER) {
                            parseStatement();
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        if (current().type == TokenType.SEMICOLON) {
            advance();
        }
    }

    void parseStatement() {
        Token varToken = current();

        if (varToken.type == TokenType.IDENTIFIER) {
            String varName = varToken.value;
            advance();

            if (current().type == TokenType.ASSIGN) {
                advance();
                int value = parseExpression();

                if (current().type == TokenType.SEMICOLON) {
                    advance();
                    variables.put(varName, value);

                    String bengaliValue = toBengaliNumber(value);
                    System.out.println(varName + " = " + bengaliValue);
                }
            }
        }
    }
}