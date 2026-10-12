import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ASTTest {

    private static final double DELTA = 1e-9;

    @Test
    void numNodeReturnsItsValue() {
        assertEquals(3.5, new NumNode(3.5).eval(), DELTA);
    }

    @Test
    void binopAdd() {
        assertEquals(7, new BinopNode("+", new NumNode(3), new NumNode(4)).eval(), DELTA);
    }

    @Test
    void binopSubtract() {
        assertEquals(-1, new BinopNode("-", new NumNode(3), new NumNode(4)).eval(), DELTA);
    }

    @Test
    void binopMultiply() {
        assertEquals(12, new BinopNode("*", new NumNode(3), new NumNode(4)).eval(), DELTA);
    }

    @Test
    void binopDivide() {
        assertEquals(1.5, new BinopNode("/", new NumNode(6), new NumNode(4)).eval(), DELTA);
    }

    @Test
    void binopModulo() {
        assertEquals(1, new BinopNode("%", new NumNode(7), new NumNode(3)).eval(), DELTA);
    }

    @Test
    void binopExponent() {
        assertEquals(64, new BinopNode("^", new NumNode(4), new NumNode(3)).eval(), DELTA);
    }

    @Test
    void nestedTreeEvaluatesChildrenFirst() {
        // 2 * (3 + 4)
        AST tree = new BinopNode("*", new NumNode(2),
                new BinopNode("+", new NumNode(3), new NumNode(4)));
        assertEquals(14, tree.eval(), DELTA);
    }

    @Test
    void unknownOperatorThrows() {
        AST bad = new BinopNode("?", new NumNode(1), new NumNode(2));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, bad::eval);
        assertEquals("invalid token", e.getMessage());
    }

    @Test
    void parsedInfixExpressionEvaluates() {
        assertEquals(14, Parser.parseInfix("2 * ( 3 + 4 )").eval(), DELTA);
    }

    @Test
    void parsedPostfixExpressionEvaluates() {
        assertEquals(14, Parser.parsePostfix("2 3 4 + *").eval(), DELTA);
    }
}