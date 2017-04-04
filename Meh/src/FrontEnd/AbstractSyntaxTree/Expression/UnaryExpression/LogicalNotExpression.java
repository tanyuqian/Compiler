package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by tan on 4/4/17.
 */
public class LogicalNotExpression extends UnaryExpression {
    public LogicalNotExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue, expression);
    }

    public static Expression getExpression(Expression expression) {
        if (expression.type instanceof IntType) {
            if (expression instanceof BoolConstant) {
                return new BoolConstant(!(((BoolConstant) expression).tag));
            }
            return new BitwiseNotExpression(new BoolType(), false, expression);
        }
        throw new CompilationError("logical-not needs bool.");
    }

}
