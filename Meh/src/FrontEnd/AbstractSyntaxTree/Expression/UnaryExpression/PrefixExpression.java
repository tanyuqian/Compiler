package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/1/17.
 */
public class PrefixExpression extends UnaryExpression {
    public String operation;

    public PrefixExpression(Type type, String operation, Expression expression) {
        super(type, operation);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "[op = \'" + operation + "\': " + expression.toString() + "]";
    }
}
