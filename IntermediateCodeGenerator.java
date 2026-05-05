import java.util.*;

public class IntermediateCodeGenerator {
    List<String> code = new ArrayList<>();
    int t = 1;

    String newTemp() {
        return "t" + t++;
    }

    void assign(String a, String b) {
        code.add(a + " = " + b);
    }

    void binary(String r, String a, String op, String b) {
        code.add(r + " = " + a + " " + op + " " + b);
    }

    void label(String l) {
        code.add(l + ":");
    }

    void print() {
        System.out.println("\nINTERMEDIATE CODE:");
        System.out.println("==================");
        for (String c : code) {
            System.out.println(c);
        }
    }
}