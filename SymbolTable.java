import java.util.*;

public class SymbolTable {

    static HashMap<String, String> table = new HashMap<>();

    public static void insert(String name, String type) {
        if (table.containsKey(name)) {
            System.out.println("Error: Variable already declared -> " + name);
        } else {
            table.put(name, type);
            System.out.println("Inserted -> " + name + " : " + type);
        }
    }

    public static boolean exists(String name) {
        return table.containsKey(name);
    }

    public static String getType(String name) {
        return table.get(name);
    }

    public static void display() {
        System.out.println("\n========== Symbol Table ==========");

        for (String key : table.keySet()) {
            System.out.println(key + " : " + table.get(key));
        }
    }
}