public class Token {
    TokenType type;
    String value;
    
    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
    
    public String toString() {
        String banglaType = "";
        if (type == TokenType.NUMBER) banglaType = "সংখ্যা";
        else if (type == TokenType.PLUS) banglaType = "যোগ চিহ্ন";
        else if (type == TokenType.MINUS) banglaType = "বিয়োগ চিহ্ন";
        else if (type == TokenType.MULTIPLY) banglaType = "গুণ চিহ্ন";
        else if (type == TokenType.ASSIGN) banglaType = "সমান চিহ্ন";
        else if (type == TokenType.IDENTIFIER) banglaType = "পরিচয়কারী";
        else if (type == TokenType.SEMICOLON) banglaType = "সেমিকোলন";
        else if (type == TokenType.IF) banglaType = "যদি";
        else if (type == TokenType.ELSE) banglaType = "নাহলে";
        else if (type == TokenType.THEN) banglaType = "তাহলে";
        else if (type == TokenType.GT) banglaType = "বড় চিহ্ন";
        else if (type == TokenType.LT) banglaType = "ছোট চিহ্ন";
        else if (type == TokenType.EQ) banglaType = "সমান চিহ্ন";
        else if (type == TokenType.LEFT_PAREN) banglaType = "বাম বন্ধনী";
        else if (type == TokenType.RIGHT_PAREN) banglaType = "ডান বন্ধনী";
        else if (type == TokenType.EOF) banglaType = "ফাইলের শেষ";
        
        return banglaType + ": " + value;
    }
}