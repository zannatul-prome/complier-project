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
    
    boolean parseCondition() {
        int left = parseExpression();
        boolean result = false;
        
        if (current().type == TokenType.GT) {
            advance();
            int right = parseExpression();
            result = (left > right);
        }
        else if (current().type == TokenType.LT) {
            advance();
            int right = parseExpression();
            result = (left < right);
        }
        else if (current().type == TokenType.EQ) {
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
                result = result + right;
            }
            else if (current().type == TokenType.MINUS) {
                advance();
                int right = parseTerm();
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
            return Integer.parseInt(token.value);
        }
        else if (token.type == TokenType.IDENTIFIER) {
            advance();
            String varName = token.value;
            if (!variables.containsKey(varName)) {
                return 0;
            }
            return variables.get(varName);
        }
        else if (token.type == TokenType.LEFT_PAREN) {
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
            if (current().type == TokenType.IF) {
                parseIfElse();
            } 
            else {
                parseStatement();
            }
        }
        
        System.out.println("\n✔ প্রোগ্রাম সঠিক!");
    }
    
    void parseIfElse() {
        advance(); // skip 'যদি'
        
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
                
                while (current().type != TokenType.ELSE && 
                       current().type != TokenType.EOF) {
                    if (current().type == TokenType.IDENTIFIER) {
                        parseStatement();
                    } else {
                        break;
                    }
                }
            }
            else {
                System.out.println("শর্ত মিথ্যা → ELSE ব্লক চলবে");
                
                while (current().type != TokenType.ELSE && 
                       current().type != TokenType.EOF) {
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