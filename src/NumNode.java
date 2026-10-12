public class NumNode implements AST {

    private final double value;

    /*
     Purpose: Create a leaf node holding a number.
     Input/output: stores the number; creates a NumNode.
     @param value: double
     */
    public NumNode(double value) {
        this.value = value;
    }

    /*
     Purpose: Evaluate this leaf, which is just the number it holds.
     Input/output: no input; returns the stored value as a double.
     */
    public double eval() {
        return value;
    }
}