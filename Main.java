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
        runCompiler(code1);
        
        System.out.println("\n\n========== প্রোগ্রাম ২ (শর্ত মিথ্যা) ==========");
        System.out.println(code2);
        runCompiler(code2);
        
        System.out.println("\n\n========== প্রোগ্রাম ৩ (সমান শর্ত) ==========");
        System.out.println(code3);
        runCompiler(code3);
    }

    private static void runCompiler(String code) {
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        System.out.println("টোকেন:");
        for (Token t : tokens) {
            t.printBangla();
        }

        Parser parser = new Parser(tokens);
        parser.parse();
    }
}