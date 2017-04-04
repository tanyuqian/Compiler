package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/4/17.
 */
public class BitwiseAndExpression extends BinaryExpression {
    public BitwiseAndExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getStatement(Expression left, Expression right) {
        if ((left.type instanceof IntType) && (right.type instanceof IntType)) {
            if ((left instanceof IntConstant) && (right instanceof IntConstant)) {
                int a = ((IntConstant) left).number, b = ((IntConstant) right).number;
                return new IntConstant(a + b);
            }
            return new BitwiseAndExpression(new IntType(), false, left, right);
        }
        throw new CompilationError("bitwise and needs two number of IntType.");
    }

}