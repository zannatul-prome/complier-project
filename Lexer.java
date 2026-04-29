import java.util.*;

public class Lexer {
    String input;
    int pos;
    
    Lexer(String input) {
        this.input = input;
        this.pos = 0;
    }
    
    boolean isBengaliDigit(char c) {
        return c >= '০' && c <= '৯';
    }
    
    boolean isBengaliLetter(char c) {
        return (c >= 'অ' && c <= 'হ') || (c >= 'ক' && c <= 'ড়');
    }
    
    String convertToEnglish(String bengaliNum) {
        String result = "";
        for (char c : bengaliNum.toCharArray()) {
            if (c == '০') result += "0";
            else if (c == '১') result += "1";
            else if (c == '২') result += "2";
            else if (c == '৩') result += "3";
            else if (c == '৪') result += "4";
            else if (c == '৫') result += "5";
            else if (c == '৬') result += "6";
            else if (c == '৭') result += "7";
            else if (c == '৮') result += "8";
            else if (c == '৯') result += "9";
            else result += c;
        }
        return result;
    }
    
    List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        
        while (pos < input.length()) {
            char c = input.charAt(pos);
            
            if (c == ' ' || c == '\n' || c == '\t') {
                pos++;
                continue;
            }
            
            // Check for numbers
            if (isBengaliDigit(c)) {
                String num = "";
                while (pos < input.length() && isBengaliDigit(input.charAt(pos))) {
                    num += input.charAt(pos);
                    pos++;
                }
                String engNum = convertToEnglish(num);
                tokens.add(new Token(TokenType.NUMBER, engNum));
                continue;
            }
            
         
            if (isBengaliLetter(c)) {
                String word = "";
                while (pos < input.length() && (isBengaliLetter(input.charAt(pos)) || 
                       isBengaliDigit(input.charAt(pos)))) {
                    word += input.charAt(pos);
                    pos++;
                }
                
                if (word.equals("যদি")) {
                    tokens.add(new Token(TokenType.IF, word));
                }
                else if (word.equals("নাহলে")) {
                    tokens.add(new Token(TokenType.ELSE, word));
                }
                else if (word.equals("তাহলে")) {
                    tokens.add(new Token(TokenType.THEN, word));
                }
                else {
                    tokens.add(new Token(TokenType.IDENTIFIER, word));
                }
                continue;
            }
            
            // Check for operators
            if (c == '=') {
                if (pos + 1 < input.length() && input.charAt(pos + 1) == '=') {
                    tokens.add(new Token(TokenType.EQ, "=="));
                    pos += 2;
                } else {
                    tokens.add(new Token(TokenType.ASSIGN, "="));
                    pos++;
                }
            }
            else if (c == '+') {
                tokens.add(new Token(TokenType.PLUS, "+"));
                pos++;
            }
            else if (c == '-') {
                tokens.add(new Token(TokenType.MINUS, "-"));
                pos++;
            }
            else if (c == '*') {
                tokens.add(new Token(TokenType.MULTIPLY, "*"));
                pos++;
            }
            else if (c == '>') {
                tokens.add(new Token(TokenType.GT, ">"));
                pos++;
            }
            else if (c == '<') {
                tokens.add(new Token(TokenType.LT, "<"));
                pos++;
            }
            else if (c == '(') {
                tokens.add(new Token(TokenType.LEFT_PAREN, "("));
                pos++;
            }
            else if (c == ')') {
                tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));
                pos++;
            }
            else if (c == ';') {
                tokens.add(new Token(TokenType.SEMICOLON, ";"));
                pos++;
            }
            else {
                pos++;
            }
        }
        
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}