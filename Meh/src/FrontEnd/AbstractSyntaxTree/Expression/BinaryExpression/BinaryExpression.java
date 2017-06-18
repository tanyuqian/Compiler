package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/1/17.
 */
public abstract class BinaryExpression extends Expression {
    public Expression left, right;

    public BinaryExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue);
        this.left = left;
        this.right = right;
    }
}
