package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/1/17.
 */
public class PostfixExpression extends UnaryExpression {
    public String operation;

    public PostfixExpression(Type type, Expression expression, String operation) {
        super(type, expression);
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "[" + expression + ": op = \"" + operation + "\"]";
    }
}
