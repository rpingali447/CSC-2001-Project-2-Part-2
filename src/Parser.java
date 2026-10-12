public class Parser {

    /*
    Purpose: Turn a postfix (RPN) expression into an AST.
    Input/output: takes an expression string; returns the root of its AST.
    @param input: String
    */
    public static AST parsePostfix(String input) {
        if (input.equals("")) {
            throw new IllegalArgumentException("empty input");
        }
        String[] tokens = input.split("\\s+");
        ArrayStack<AST> exprs = new ArrayStack<>();

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*")
                    || token.equals("/") || token.equals("%") || token.equals("^")) {
                applyOperator(exprs, token);
            } else {
                try {
                    exprs.push(new NumNode(Double.parseDouble(token)));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid token");
                }
            }
        }

        if (exprs.size() > 1) {
            throw new IllegalArgumentException("too many operands");
        }
        return exprs.pop();
    }

    /*
    Purpose: Turn an infix expression into an AST using the shunting-yard algorithm.
    Input/output: takes an expression string; returns the root of its AST.
    @param input: String
    */
    public static AST parseInfix(String input) {
        if (input.equals("")) {
            throw new IllegalArgumentException("empty input");
        }
        String[] tokens = input.split("\\s+");
        ArrayStack<AST> exprs = new ArrayStack<>();
        ArrayStack<String> ops = new ArrayStack<>();

        for (String token : tokens) {
            if (token.equals("(")) {
                ops.push(token);
            } else if (token.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    applyOperator(exprs, ops.pop());
                }
                if (ops.isEmpty()) {
                    throw new IllegalArgumentException("mismatched close paren");
                }
                ops.pop();
            } else if (token.equals("+") || token.equals("-") || token.equals("*")
                    || token.equals("/") || token.equals("%") || token.equals("^")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    int top = precedence(ops.peek());
                    int current = precedence(token);
                    if (top > current || (top == current && !token.equals("^"))) {
                        applyOperator(exprs, ops.pop());
                    } else {
                        break;
                    }
                }
                ops.push(token);
            } else {
                try {
                    exprs.push(new NumNode(Double.parseDouble(token)));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid token");
                }
            }
        }

        while (!ops.isEmpty()) {
            String op = ops.pop();
            if (op.equals("(")) {
                throw new IllegalArgumentException("mismatched open paren");
            }
            applyOperator(exprs, op);
        }

        if (exprs.isEmpty()) {
            throw new IllegalArgumentException("insufficient operands");
        }
        if (exprs.size() > 1) {
            throw new IllegalArgumentException("too many operands");
        }
        return exprs.pop();
    }

    /*
    Purpose: Give the precedence of an operator (higher means it goes first).
    Input/output: takes an operator; returns 3 for ^, 2 for * / %, 1 for + -.
    @param op: String
    */
    public static int precedence(String op) {
        if (op.equals("^")) {
            return 3;
        }
        if (op.equals("*") || op.equals("/") || op.equals("%")) {
            return 2;
        }
        return 1;
    }

    /*
    Purpose: Pop two ASTs, combine them with the operator, and push the result.
    Input/output: changes the stack; returns nothing.
    @param exprs: ArrayStack<AST>
    @param op: String
    */
    public static void applyOperator(ArrayStack<AST> exprs, String op) {
        if (exprs.size() < 2) {
            throw new IllegalArgumentException("insufficient operands");
        }
        AST right = exprs.pop();
        AST left = exprs.pop();
        exprs.push(new BinopNode(op, left, right));
    }
}