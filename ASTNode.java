public class ASTNode {
    public enum NodeType {
        PROGRAM,
        ASSIGNMENT,
        BINARY_OP,
        NUMBER_LITERAL,
        VARIABLE,
        IF_STATEMENT,
        BLOCK
    }
    
    NodeType type;
    String value;
    ASTNode left, right;
    
    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.token = token;
        this.children = new ArrayList<>();
    }
    
    public ASTNode(String type, ASTNode left, ASTNode right) {
        this.type = type;
        this.left = condition;
        this.right = thenBlock;
        this.children = new ArrayList<>();
        if (elseBlock != null) {
            this.children.add(elseBlock);
        }
    }
    
    public void addChild(ASTNode child) {
        children.add(child);
    }
    
    public void printAST() {
        printAST("", true);
    }
    
    private void printAST(String prefix, boolean isLast) {
        String nodeStr = "";
        switch(type) {
            case PROGRAM:
                nodeStr = "প্রোগ্রাম";
                break;
            case ASSIGNMENT:
                nodeStr = "নির্ধারণ: " + value;
                break;
            case BINARY_OP:
                nodeStr = "অপারেশন: " + token.value;
                break;
            case NUMBER_LITERAL:
                nodeStr = "সংখ্যা: " + value;
                break;
            case VARIABLE:
                nodeStr = "ভেরিয়েবল: " + value;
                break;
            case IF_STATEMENT:
                nodeStr = "IF-ELSE স্টেটমেন্ট";
                break;
            case BLOCK:
                nodeStr = "ব্লক";
                break;
        }
        
        System.out.print(prefix);
        System.out.print(isLast ? "└── " : "├── ");
        System.out.println(nodeStr);
        
        String newPrefix = prefix + (isLast ? "    " : "│   ");
        
        if (type == NodeType.BINARY_OP) {
            if (left != null) left.printAST(newPrefix, false);
            if (right != null) right.printAST(newPrefix, true);
        } else if (type == NodeType.IF_STATEMENT) {
            if (left != null) {
                System.out.println(newPrefix + "├── শর্ত:");
                left.printAST(newPrefix + "│   ", false);
            }
            if (right != null) {
                System.out.println(newPrefix + "├── তাহলে:");
                right.printAST(newPrefix + "│   ", false);
            }
            if (children.size() > 0 && children.get(0) != null) {
                System.out.println(newPrefix + "└── নাহলে:");
                children.get(0).printAST(newPrefix + "    ", true);
            }
        } else if (type == NodeType.BLOCK) {
            for (int i = 0; i < children.size(); i++) {
                children.get(i).printAST(newPrefix, i == children.size() - 1);
            }
        }
    }
}