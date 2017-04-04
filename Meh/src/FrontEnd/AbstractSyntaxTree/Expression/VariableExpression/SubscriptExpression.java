package FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/1/17.
 */
public class SubscriptExpression extends Expression {
    public Expression expression;
    public Expression subscript;

    public SubscriptExpression(Type type, boolean isLeftValue, Expression expression, Expression subscript) {
        super(type, isLeftValue);
        this.expression = expression;
        this.subscript = subscript;
    }

    public static SubscriptExpression getExpression(Expression expression, Expression subscript) {
        if (!(expression.type instanceof ArrayType)) {
            throw new CompilationError("subscript expression's expression must be ArrayType");
        }
        if (!(subscript.type instanceof IntType)) {
            throw new CompilationError("subscript expression's subscript must be IntType");
        }
        ArrayType arrayType = (ArrayType)expression.type;
        return new SubscriptExpression(arrayType.reduce(), expression.isLeftValue, expression, subscript);
    }
}
