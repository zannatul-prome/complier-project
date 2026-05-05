import java.util.*;

public class SymbolTable {
    public static class Symbol {
        String name;
        String type;    // "int", "float", etc.
        int value;
        int scope;
        boolean initialized;
        int lineDeclared;
        
        Symbol(String name, int scope, int line) {
            this.name = name;
            this.type = "int";
            this.scope = scope;
            this.initialized = false;
            this.value = 0;
            this.lineDeclared = line;
        }
        
        void setValue(int value, int line) {
            this.value = value;
            this.initialized = true;
        }
        
        public String toString() {
            return String.format("%-15s %-10s %-8d %-12s %-6d", 
                name, type, scope, (initialized ? "হ্যাঁ" : "না"), value);
        }
    }
    
    private List<Map<String, Symbol>> scopes;
    private int currentScope;
    private Map<String, Symbol> allSymbols;
    private List<String> warnings;
    
    public SymbolTable() {
        scopes = new ArrayList<>();
        allSymbols = new HashMap<>();
        warnings = new ArrayList<>();
        currentScope = 0;
        enterScope(); // Global scope
    }
    
    public void enterScope() {
        scopes.add(new HashMap<>());
        currentScope = scopes.size() - 1;
    }
    
    public void exitScope() {
        if (scopes.size() > 1) {
            Map<String, Symbol> scopeToRemove = scopes.get(currentScope);
            scopes.remove(currentScope);
            currentScope--;
        }
    }
    
    public boolean addSymbol(String name, int line) {
        // Check if already declared in current scope
        if (scopes.get(currentScope).containsKey(name)) {
            warnings.add("Warning: Variable '" + name + "' already declared in this scope at line " + line);
            return false;
        }
        
        Symbol symbol = new Symbol(name, currentScope, line);
        scopes.get(currentScope).put(name, symbol);
        allSymbols.put(name, symbol);
        return true;
    }
    
    public Symbol lookup(String name) {
        // Look from current scope outward
        for (int i = currentScope; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return scopes.get(i).get(name);
            }
        }
        return null;
    }
    
    public boolean isDeclared(String name) {
        return lookup(name) != null;
    }
    
    public boolean isInitialized(String name) {
        Symbol sym = lookup(name);
        return sym != null && sym.initialized;
    }
    
    public void setValue(String name, int value, int line) {
        Symbol sym = lookup(name);
        if (sym != null) {
            sym.setValue(value, line);
        } else {
            warnings.add("Warning: Variable '" + name + "' used before declaration at line " + line);
            addSymbol(name, line);
            sym = lookup(name);
            sym.setValue(value, line);
        }
    }
    
    public int getValue(String name) {
        Symbol sym = lookup(name);
        return sym != null ? sym.value : 0;
    }
    
    public void printSymbolTable() {
        System.out.println("\n📊 সিম্বল টেবিল (Symbol Table)");
        System.out.println("=================================================");
        System.out.printf("%-15s %-10s %-8s %-12s %-6s\n", 
            "নাম (Name)", "টাইপ (Type)", "স্কোপ", "ইনিশিয়ালাইজড", "মান");
        System.out.println("-------------------------------------------------");
        
        for (Map<String, Symbol> scope : scopes) {
            for (Symbol sym : scope.values()) {
                System.out.println(sym);
            }
        }
        System.out.println("=================================================\n");
        
        if (!warnings.isEmpty()) {
            System.out.println("⚠ সতর্কতা (Warnings):");
            for (String warning : warnings) {
                System.out.println("  - " + warning);
            }
            System.out.println();
        }
    }
    
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }
}