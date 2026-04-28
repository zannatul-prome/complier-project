import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Test program 1: IF condition true
        String code1 = "ক = ১০;\n" +
                       "যদি (ক > ৫) তাহলে\n" +
                       "    খ = ১০০;\n" +
                       "নাহলে\n" +
                       "    খ = ৫০;\n" +
                       "গ = খ * ২;\n";
        
        // Test program 2: IF condition false
        String code2 = "ক = ৩;\n" +
                       "যদি (ক > ৫) তাহলে\n" +
                       "    খ = ১০০;\n" +
                       "নাহলে\n" +
                       "    খ = ৫০;\n" +
                       "গ = খ * ২;\n";
        
        // Test program 3: Equal condition
        String code3 = "ক = ১০;\n" +
                       "যদি (ক == ১০) তাহলে\n" +
                       "    খ = ২০০;\n" +
                       "নাহলে\n" +
                       "    খ = ১০০;\n" +
                       "গ = খ + ৫;\n";
        
        System.out.println("========== প্রোগ্রাম ১ (শর্ত সত্য) ==========");
        System.out.println(code1);
        System.out.println("==================");
        
        Lexer lexer1 = new Lexer(code1);
        List<Token> tokens1 = lexer1.tokenize();
        
        System.out.println("টোকেন:");
        for (Token t : tokens1) {
            System.out.println(t);
        }
        
        Parser parser1 = new Parser(tokens1);
        parser1.parse();
        
        System.out.println("\n\n========== প্রোগ্রাম ২ (শর্ত মিথ্যা) ==========");
        System.out.println(code2);
        System.out.println("==================");
        
        Lexer lexer2 = new Lexer(code2);
        List<Token> tokens2 = lexer2.tokenize();
        
        System.out.println("টোকেন:");
        for (Token t : tokens2) {
            System.out.println(t);
        }
        
        Parser parser2 = new Parser(tokens2);
        parser2.parse();
        
        System.out.println("\n\n========== প্রোগ্রাম ৩ (সমান শর্ত) ==========");
        System.out.println(code3);
        System.out.println("==================");
        
        Lexer lexer3 = new Lexer(code3);
        List<Token> tokens3 = lexer3.tokenize();
        
        System.out.println("টোকেন:");
        for (Token t : tokens3) {
            System.out.println(t);
        }
        
        Parser parser3 = new Parser(tokens3);
        parser3.parse();
    }
}