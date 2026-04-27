// ===============================
// File: Main.java
// ===============================

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String sourceCode =
                "সংখ্যা অ; " +
                "সংখ্যা ক; " +
                "দশমিক গড়; " +

                "অ = ৫; " +
                "ক = অ + ৫ * ( ৩ - ১ ); " +
                "গড় = ৩.৫; ";

        List<Token> tokens = Lexer.tokenize(sourceCode);

        System.out.println("========== TOKENS ==========");

        for (Token token : tokens) {
            System.out.println(token);
        }

        System.out.println();

        Parser.parse(tokens);

        SymbolTable.display();
    }
}