package FrontEnd.AbstractSyntaxTree.Statement;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class ExpressionStatement extends Statement {
    public Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    public static ExpressionStatement getStatement(Expression expression) {
        return new ExpressionStatement(expression);
    }

}
