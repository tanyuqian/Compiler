package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/1/17.
 */
public abstract class UnaryExpression extends Expression {
    public Expression expression;

    public UnaryExpression(Type type, Expression expression) {
        super(type);
        this.expression = expression;
    }
}
