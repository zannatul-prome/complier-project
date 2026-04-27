import java.util.*;

public class Lexer {

    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();

        input = input.replace(";", " ; ")
                     .replace("=", " = ")
                     .replace("+", " + ")
                     .replace("-", " - ")
                     .replace("*", " * ")
                     .replace("/", " / ")
                     .replace("(", " ( ")
                     .replace(")", " ) ");

        String[] words = input.split("\\s+");

        for (String word : words) {

            if (word.equals("সংখ্যা") || word.equals("দশমিক")) {
                tokens.add(new Token("TYPE", word));
            }

            // Bangla float number (example: ৩.৫)
            else if (word.matches("[০-৯]+\\.[০-৯]+")) {
                tokens.add(new Token("FLOAT", word));
            }

            // Bangla integer number (example: ২৫)
            else if (word.matches("[০-৯]+")) {
                tokens.add(new Token("NUMBER", word));
            }

            // Bangla variable names
            else if (word.matches("[ঀ-৿]+")) {
                tokens.add(new Token("IDENTIFIER", word));
            }

            else if (word.equals("=")) {
                tokens.add(new Token("ASSIGN", "="));
            }

            else if (word.equals("+")) {
                tokens.add(new Token("PLUS", "+"));
            }

            else if (word.equals("-")) {
                tokens.add(new Token("MINUS", "-"));
            }

            else if (word.equals("*")) {
                tokens.add(new Token("MULTIPLY", "*"));
            }

            else if (word.equals("/")) {
                tokens.add(new Token("DIVIDE", "/"));
            }

            else if (word.equals("(")) {
                tokens.add(new Token("LEFT_PAREN", "("));
            }

            else if (word.equals(")")) {
                tokens.add(new Token("RIGHT_PAREN", ")"));
            }

            else if (word.equals(";")) {
                tokens.add(new Token("SEMICOLON", ";"));
            }

            else if (!word.isEmpty()) {
                System.out.println("Invalid Token -> " + word);
            }
        }

        return tokens;
    }
}