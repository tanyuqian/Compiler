package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.NullConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.StringConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/4/17.
 */
public class NotEqualToExpression extends BinaryExpression {
    public NotEqualToExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if (!left.type.compatibleWith(right.type)) {
            throw new CompilationError("Type error in not-equal-to expression.");
        }
        if ((left instanceof NullConstant) && (right instanceof NullConstant)) {
            return new BoolConstant(false);
        } else if ((left instanceof BoolConstant) && (right instanceof BoolConstant)) {
            return new BoolConstant(((BoolConstant) left).tag != ((BoolConstant) right).tag);
        } else if ((left instanceof IntConstant) && (right instanceof IntConstant)) {
            return new BoolConstant(((IntConstant) left).number != ((IntConstant) right).number);
        } else if ((left instanceof StringConstant) && (right instanceof StringConstant)) {
            return new BoolConstant(!(((StringConstant) left).str.equals(((StringConstant) right).str)));
        }
        return new NotEqualToExpression(new BoolType(), false, left, right);
    }
}
