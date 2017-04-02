package FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/1/17.
 */
public class SubscriptExpression extends Expression {
    public Expression expression;
    public Expression subscript;

    public SubscriptExpression(Type type, Expression expression, Expression subscript) {
        super(type);
        this.expression = expression;
        this.subscript = subscript;
    }
}
