import java.util.*;

public class Main {
    public static void main(String[] args) {
        // বাংলায় প্রোগ্রাম লিখি
        String code = "ক = ৫;\n" +
                      "খ = ক + ৩;\n" +
                      "গ = খ * ২;\n";
        
        System.out.println("বাংলা প্রোগ্রাম:");
        System.out.println(code);
        System.out.println("==================");
        
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();
        
        System.out.println("টোকেন:");
        for (Token t : tokens) {
            System.out.println(t);
        }
        
        System.out.println("\nফলাফল:");
        Parser parser = new Parser(tokens);
        parser.parse();
    }
}