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
    
    String toBengaliNumber(String num) {
        String result = "";
        for (char c : num.toCharArray()) {
            if (c == '0') result += '০';
            else if (c == '1') result += '১';
            else if (c == '2') result += '২';
            else if (c == '3') result += '৩';
            else if (c == '4') result += '৪';
            else if (c == '5') result += '৫';
            else if (c == '6') result += '৬';
            else if (c == '7') result += '৭';
            else if (c == '8') result += '৮';
            else if (c == '9') result += '৯';
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
            
            // বাংলা সংখ্যা চিনি
            if (isBengaliDigit(c)) {
                String num = "";
                while (pos < input.length() && isBengaliDigit(input.charAt(pos))) {
                    num += input.charAt(pos);
                    pos++;
                }
                String engNum = convertToEnglish(num);
                String banglaNum = toBengaliNumber(engNum);
                tokens.add(new Token(TokenType.NUMBER, banglaNum));
                continue;
            }
            
            // বাংলা অক্ষর চিনি (ভেরিয়েবল)
            if (c >= 'অ' && c <= 'হ') {
                String name = "";
                while (pos < input.length() && input.charAt(pos) >= 'অ' && input.charAt(pos) <= 'হ') {
                    name += input.charAt(pos);
                    pos++;
                }
                tokens.add(new Token(TokenType.IDENTIFIER, name));
                continue;
            }
            
            // অপারেটর
            if (c == '=') {
                tokens.add(new Token(TokenType.ASSIGN, "="));
                pos++;
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
                tokens.add(new Token(TokenType.MULTIPLY, "×"));
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