public class Token {
    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    void printBangla() {

        if (type == TokenType.IDENTIFIER)
            System.out.println("পরিচয়কারী: " + value);

        else if (type == TokenType.NUMBER)
            System.out.println("সংখ্যা: " + value);

        else if (type == TokenType.ASSIGN)
            System.out.println("সমান চিহ্ন: =");

        else if (type == TokenType.PLUS)
            System.out.println("যোগ চিহ্ন: +");

        else if (type == TokenType.MULTIPLY)
            System.out.println("গুণ চিহ্ন: *");

        else if (type == TokenType.GT)
            System.out.println("বড় চিহ্ন: >");

        else if (type == TokenType.LEFT_PAREN)
            System.out.println("বাম বন্ধনী: (");

        else if (type == TokenType.RIGHT_PAREN)
            System.out.println("ডান বন্ধনী: )");

        else if (type == TokenType.SEMICOLON)
            System.out.println("সেমিকোলন: ;");

        else if (type == TokenType.IF)
            System.out.println("যদি: যদি");

        else if (type == TokenType.THEN)
            System.out.println("তাহলে: তাহলে");

        else if (type == TokenType.ELSE)
            System.out.println("নাহলে: নাহলে");

        else if (type == TokenType.EOF)
            System.out.println("ফাইলের শেষ:");
    }
}