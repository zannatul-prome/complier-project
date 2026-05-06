
// AST Node class for syntax tree
public class ASTNode {
    String type;
    String value;
    ASTNode left, right;

    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public ASTNode(String type, ASTNode left, ASTNode right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }
}