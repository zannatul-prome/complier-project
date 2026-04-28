public class Token {
    TokenType type;
    String value;
    
    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
    
    public String toString() {
        String banglaType = "";
        switch(type) {
            case NUMBER: banglaType = "সংখ্যা"; break;
            case PLUS: banglaType = "যোগ চিহ্ন"; break;
            case MINUS: banglaType = "বিয়োগ চিহ্ন"; break;
            case MULTIPLY: banglaType = "গুণ চিহ্ন"; break;
            case ASSIGN: banglaType = "সমান চিহ্ন"; break;
            case IDENTIFIER: banglaType = "পরিচয়কারী"; break;
            case SEMICOLON: banglaType = "সেমিকোলন"; break;
            case EOF: banglaType = "ফাইলের শেষ"; break;
        }
        return banglaType + ": " + value;
    }
}