package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/4/17.
 */
public class UnaryMinusExpression extends UnaryExpression {
    public UnaryMinusExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue, expression);
    }

    public static Expression getExpression(Expression expression) {
        if (expression.type instanceof IntType) {
            if (expression instanceof IntConstant) {
                return new IntConstant(-((IntConstant) expression).number);
            }
            return new UnaryMinusExpression(new IntType(), false, expression);
        }
        throw new CompilationError("unary-minus: type error.");
    }
}
