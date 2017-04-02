package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/1/17.
 */
public class BinaryExpression extends Expression {
    public Expression left, right;
    public String operation;

    public BinaryExpression(Type type, Expression left, String operation, Expression right) {
        super(type);
        this.left = left;
        this.operation = operation;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + operation + right.toString();
    }
}
