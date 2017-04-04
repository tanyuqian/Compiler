package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/3/17.
 */
public class AssignmentExpression extends BinaryExpression {
    public AssignmentExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if (!left.isLeftValue) {
            throw new CompilationError("left Value Error.");
        }
        if (!left.type.compatibleWith(right.type)) {
            throw new CompilationError("can't compatible in assignement expression");
        }
        return new AssignmentExpression(left.type, true, left, right);
    }
}
