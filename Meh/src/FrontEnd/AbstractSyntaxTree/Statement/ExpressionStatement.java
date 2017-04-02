package FrontEnd.AbstractSyntaxTree.Statement;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;

/**
 * Created by tan on 4/1/17.
 */
public class ExpressionStatement extends Statement {
    public Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }
}
