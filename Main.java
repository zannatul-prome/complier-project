import java.util.*;

public class Main {
    public static void main(String[] args) {

        String code =
                "ক = ০;\n" +
                "যদি (ক > ৫) তাহলে\n" +
                "খ = ১০০;\n" +
                "নাহলে\n" +
                "খ = ৫০;\n" +
                "গ = খ * ২;\n";

        System.out.println("========== SIMPLE IF-ELSE ==========");

        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        System.out.println("\nটোকেন:");
        for (Token t : tokens) {
            t.printBangla();
        }

        System.out.println("\nফলাফল:");
        System.out.println("========");

        Parser parser = new Parser(tokens);
        parser.parse();
    }
}