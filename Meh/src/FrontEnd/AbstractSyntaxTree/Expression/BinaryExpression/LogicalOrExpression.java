package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/4/17.
 */
public class LogicalOrExpression extends BinaryExpression {
    public LogicalOrExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if ((left.type instanceof BoolType) && (right.type instanceof BoolType)) {
            if ((left instanceof BoolConstant) && (right instanceof BoolConstant)) {
                return new BoolConstant(((BoolConstant) left).tag || ((BoolConstant) right).tag);
            }
            return new LogicalOrExpression(new BoolType(), false, left, right);
        }
        throw new CompilationError("Type Error occur in \"||\"");
    }

}
