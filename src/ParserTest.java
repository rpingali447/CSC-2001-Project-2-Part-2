import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    // ---------- parsePostfix: valid ----------

    @Test
    void postfixSingleNumber() {
        assertEquals(5, Parser.parsePostfix("5").eval(), 0.001);
    }

    @Test
    void postfixAddition() {
        assertEquals(5, Parser.parsePostfix("2 3 +").eval(), 0.001);
    }

    @Test
    void postfixOperatorOrderMatters() {
        assertEquals(1, Parser.parsePostfix("4 3 -").eval(), 0.001);
        assertEquals(2, Parser.parsePostfix("8 4 /").eval(), 0.001);
    }

    @Test
    void postfixLongerExpression() {
        assertEquals(14, Parser.parsePostfix("5 1 2 + 4 * + 3 -").eval(), 0.001);
    }

    @Test
    void postfixExponentAndModulo() {
        assertEquals(8, Parser.parsePostfix("2 3 ^").eval(), 0.001);
        assertEquals(1, Parser.parsePostfix("7 3 %").eval(), 0.001);
    }

    @Test
    void postfixDecimals() {
        assertEquals(4.0, Parser.parsePostfix("1.5 2.5 +").eval(), 0.001);
    }

    // ---------- parsePostfix: errors ----------

    @Test
    void postfixEmptyInput() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parsePostfix(""));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    void postfixInvalidToken() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parsePostfix("2 a +"));
        assertEquals("invalid token", e.getMessage());
    }

    @Test
    void postfixInsufficientOperands() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parsePostfix("2 +"));
        assertEquals("insufficient operands", e.getMessage());
    }

    @Test
    void postfixTooManyOperands() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parsePostfix("2 3 4 +"));
        assertEquals("too many operands", e.getMessage());
    }

    // ---------- parseInfix: valid ----------

    @Test
    void infixSingleNumber() {
        assertEquals(5, Parser.parseInfix("5").eval(), 0.001);
    }

    @Test
    void infixParenthesesOverridePrecedence() {
        assertEquals(14, Parser.parseInfix("2 * ( 3 + 4 )").eval(), 0.001);
        assertEquals(9, Parser.parseInfix("( 1 + 2 ) * 3").eval(), 0.001);
    }

    @Test
    void infixMultiplicationBeforeAddition() {
        assertEquals(14, Parser.parseInfix("2 + 3 * 4").eval(), 0.001);
        assertEquals(10, Parser.parseInfix("2 * 3 + 4").eval(), 0.001);
    }

    @Test
    void infixLeftAssociativeOperators() {
        assertEquals(3, Parser.parseInfix("10 - 4 - 3").eval(), 0.001);
        assertEquals(1.5, Parser.parseInfix("2 * 3 / 4").eval(), 0.001);
    }

    @Test
    void infixExponentHasHigherPrecedence() {
        assertEquals(128, Parser.parseInfix("2 * 4 ^ 3").eval(), 0.001);
    }

    @Test
    void infixExponentIsRightAssociative() {
        assertEquals(512, Parser.parseInfix("2 ^ 3 ^ 2").eval(), 0.001);
    }

    @Test
    void infixModulo() {
        assertEquals(1, Parser.parseInfix("7 % 3").eval(), 0.001);
    }

    @Test
    void infixNestedParentheses() {
        assertEquals(20, Parser.parseInfix("( ( 2 + 3 ) * ( 1 + 3 ) )").eval(), 0.001);
    }

    // ---------- parseInfix: errors ----------

    @Test
    void infixEmptyInput() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix(""));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    void infixInvalidToken() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("2 + a"));
        assertEquals("invalid token", e.getMessage());
    }

    @Test
    void infixInsufficientOperands() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("2 +"));
        assertEquals("insufficient operands", e.getMessage());
    }

    @Test
    void infixEmptyParensInsufficientOperands() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("( )"));
        assertEquals("insufficient operands", e.getMessage());
    }

    @Test
    void infixTooManyOperands() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("2 3 4 +"));
        assertEquals("too many operands", e.getMessage());
    }

    @Test
    void infixMismatchedOpenParen() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("( 2 + 3"));
        assertEquals("mismatched open paren", e.getMessage());
    }

    @Test
    void infixMismatchedCloseParen() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("2 + 3 )"));
        assertEquals("mismatched close paren", e.getMessage());
    }
}