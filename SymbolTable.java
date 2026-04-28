import java.util.*;

public class SymbolTable {
    private HashMap<String, Object> symbols = new HashMap<>();
    private HashMap<String, String> types = new HashMap<>();
    
    public void declare(String name, String type) {
        symbols.put(name, null);
        types.put(name, type);
    }
    
    public void set(String name, Object value) {
        if (!symbols.containsKey(name)) {
            System.err.println("Error: Variable '" + name + "' not declared");
            return;
        }
        
        String type = types.get(name);
        if (type.equals("সংখ্যা") && value instanceof Integer) {
            symbols.put(name, value);
        } else if (type.equals("দশমিক") && (value instanceof Integer || value instanceof Double)) {
            if (value instanceof Integer) {
                symbols.put(name, ((Integer) value).doubleValue());
            } else {
                symbols.put(name, value);
            }
        } else {
            System.err.println("Error: Type mismatch for variable '" + name + "'");
        }
    }
    
    public Object get(String name) {
        return symbols.get(name);
    }
    
    public String getType(String name) {
        return types.get(name);
    }
    
    public static void display() {
        // This method is called from your Main class
        System.out.println("Symbol table displayed");
    }
}