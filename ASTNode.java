public class ASTNode {

    String type;
    String value;
    ASTNode left;
    ASTNode right;

    // Leaf node 
    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.left = null;
        this.right = null;
    }


    public ASTNode(String type, ASTNode left, ASTNode right) {
        this.type = type;
        this.value = null; // internal node usually value na
        this.left = left;
        this.right = right;
    }
}