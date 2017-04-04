package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/4/17.
 */
public class PrefixDecrementExpression extends UnaryExpression {
    public PrefixDecrementExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue, expression);
    }

    public static Expression getExpression(Expression expression) {
        if (!expression.isLeftValue) {
            throw new CompilationError("preftix: left-value error");
        }
        if (expression.type instanceof IntType) {
            return new PrefixDecrementExpression(new IntType(), false, expression);
        }
        throw new CompilationError("prefix: type error");
    }

}
