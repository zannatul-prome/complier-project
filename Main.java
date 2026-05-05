import java.util.*;

public class Main {
    public static void main(String[] args) {

        String code =
                "ক = ১০;\n" +
                "খ = ২০;\n" +
                "গ = ক + খ;\n" +
                "যদি (ক > ৫) তাহলে\n" +
                "ঘ = ১০০;\n" +
                "$invalid = ৫০;\n" +
                "ঙ = ঘ + ২০;\n" +
                "নাহলে\n" +
                "ঘ = ৫০;\n" +
                "চ = গ * ২;\n";

        Lexer lx = new Lexer(code);
        List<Token> tokens = lx.tokenize();

        lx.showErrors();

        Parser p = new Parser(tokens);
        p.parse();
    }
}