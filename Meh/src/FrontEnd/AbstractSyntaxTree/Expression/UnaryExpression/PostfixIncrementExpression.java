package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/4/17.
 */
public class PostfixIncrementExpression extends UnaryExpression {
    public PostfixIncrementExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue, expression);
    }

    public static Expression getExpression(Expression expression) {
        if (!expression.isLeftValue) {
            throw new CompilationError("postftix: left-value error");
        }
        if (expression.type instanceof IntType) {
            return new PostfixIncrementExpression(new IntType(), false, expression);
        }
        throw new CompilationError("postfix: type error");

    }
}