package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;

/**
 * Created by tan on 4/1/17.
 */
public class PrefixExpression extends UnaryExpression {
    public String operation;

    public PrefixExpression(String operation, Expression expression) {
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "[op = \'" + operation + "\': " + expression.toString() + "]";
    }
}
