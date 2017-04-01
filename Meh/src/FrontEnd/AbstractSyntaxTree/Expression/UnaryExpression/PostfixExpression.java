package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;

/**
 * Created by tan on 4/1/17.
 */
public class PostfixExpression extends UnaryExpression {
    public String operation;

    public PostfixExpression(Expression expression, String operation) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "[" + expression + ": op = \"" + operation + "\"]";
    }
}
