import java.util.*;

public class Lexer {
    String input;
    int pos = 0;

    Lexer(String input) {
        this.input = input;
    }

    List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {

            char c = input.charAt(pos);

            if (Character.isWhitespace(c)) {
                pos++;
                continue;
            }

            // number (Bangla + English)
            if (Character.isDigit(c) || (c >= '০' && c <= '৯')) {
                String num = "";
                while (pos < input.length() &&
                        (Character.isDigit(input.charAt(pos)) ||
                         (input.charAt(pos) >= '০' && input.charAt(pos) <= '৯'))) {
                    num += input.charAt(pos++);
                }
                tokens.add(new Token(TokenType.NUMBER, num));
                continue;
            }

            if (input.startsWith("যদি", pos)) {
                tokens.add(new Token(TokenType.IF, "যদি"));
                pos += 3;
                continue;
            }

            if (input.startsWith("নাহলে", pos)) {
                tokens.add(new Token(TokenType.ELSE, "নাহলে"));
                pos += 5;
                continue;
            }

            if (input.startsWith("তাহলে", pos)) {
                tokens.add(new Token(TokenType.THEN, "তাহলে"));
                pos += 5;
                continue;
            }

            if (Character.isLetter(c) || (c >= 'ক' && c <= 'হ')) {
                String id = "";
                while (pos < input.length() &&
                        (Character.isLetterOrDigit(input.charAt(pos)) ||
                         (input.charAt(pos) >= 'ক' && input.charAt(pos) <= 'হ'))) {
                    id += input.charAt(pos++);
                }
                tokens.add(new Token(TokenType.IDENTIFIER, id));
                continue;
            }

            switch (c) {
                case '=': tokens.add(new Token(TokenType.ASSIGN, "=")); break;
                case '+': tokens.add(new Token(TokenType.PLUS, "+")); break;
                case '*': tokens.add(new Token(TokenType.MULTIPLY, "*")); break;
                case '>': tokens.add(new Token(TokenType.GT, ">")); break;
                case '(': tokens.add(new Token(TokenType.LEFT_PAREN, "(")); break;
                case ')': tokens.add(new Token(TokenType.RIGHT_PAREN, ")")); break;
                case ';': tokens.add(new Token(TokenType.SEMICOLON, ";")); break;
            }

            pos++;
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}