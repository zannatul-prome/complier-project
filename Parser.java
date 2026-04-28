import java.util.*;

public class Parser {
    List<Token> tokens;
    int pos;
    Map<String, Integer> variables;
    
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
    
    int parseExpression() {
        int result = parseTerm();
        
        while (current().type == TokenType.PLUS || current().type == TokenType.MINUS) {
            Token op = current();
            advance();
            int right = parseTerm();
            
            if (op.type == TokenType.PLUS) {
                result = result + right;
            } else {
                result = result - right;
            }
        }
        
        return result;
    }
    
    int parseTerm() {
        int result = parseFactor();
        
        while (current().type == TokenType.MULTIPLY) {
            advance();
            int right = parseFactor();
            result = result * right;
        }
        
        return result;
    }
    
    int parseFactor() {
        Token token = current();
        
        if (token.type == TokenType.NUMBER) {
            advance();
            // Convert Bengali number to integer
            String englishNum = "";
            for (char c : token.value.toCharArray()) {
                if (c == '০') englishNum += "0";
                else if (c == '১') englishNum += "1";
                else if (c == '২') englishNum += "2";
                else if (c == '৩') englishNum += "3";
                else if (c == '৪') englishNum += "4";
                else if (c == '৫') englishNum += "5";
                else if (c == '৬') englishNum += "6";
                else if (c == '৭') englishNum += "7";
                else if (c == '৮') englishNum += "8";
                else if (c == '৯') englishNum += "9";
            }
            return Integer.parseInt(englishNum);
        }
        else if (token.type == TokenType.IDENTIFIER) {
            advance();
            String varName = token.value;
            if (!variables.containsKey(varName)) {
                return 0;
            }
            return variables.get(varName);
        }
        
        return 0;
    }
    
    String toBengaliNumber(int num) {
        String englishNum = String.valueOf(num);
        String result = "";
        for (char c : englishNum.toCharArray()) {
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
        }
        return result;
    }
    
    void parse() {
        System.out.println("\nফলাফল:");
        System.out.println("========");
        
        while (current().type != TokenType.EOF) {
            parseStatement();
        }
        
        System.out.println("\n✔ প্রোগ্রাম সঠিক!");
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