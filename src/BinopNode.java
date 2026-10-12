public class BinopNode implements AST {

    private final String operator;
    private final AST left;
    private final AST right;

    /*
     Purpose: Create an operation node from an operator and two subtrees.
     Input/output: stores the parts; creates a BinopNode.
     @param operator: String (one of + - * / % ^)
     @param left: AST
     @param right: AST
     */
    public BinopNode(String operator, AST left, AST right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    /*
     Purpose: Evaluate both children, then combine them using the operator.
     Input/output: no input; returns the result as a double.
     Throws IllegalArgumentException("invalid token") for an unknown operator.
     */
    public double eval() {
        double a = left.eval();
        double b = right.eval();
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "%":
                return a % b;
            case "^":
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("invalid token");
        }
    }
}