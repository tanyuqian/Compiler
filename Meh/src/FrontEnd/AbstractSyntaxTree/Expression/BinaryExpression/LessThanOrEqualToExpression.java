package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.StringConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

/**
 * Created by tan on 4/4/17.
 */
public class LessThanOrEqualToExpression extends BinaryExpression {
    public LessThanOrEqualToExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if ((left.type instanceof IntType) && (right.type instanceof IntType)) {
            if ((left instanceof IntConstant) && (right instanceof IntConstant)) {
                return new BoolConstant(((IntConstant) left).number <= ((IntConstant) right).number);
            }
            return new LessThanOrEqualToExpression(new BoolType(), false, left, right);
        } else if ((left.type instanceof StringType) && (right.type instanceof StringType)) {
            if ((left instanceof StringConstant) && (right instanceof StringConstant)) {
                return new BoolConstant(((StringConstant) left).str.compareTo(((StringConstant) right).str) <= 0);
            }
            return new LessThanOrEqualToExpression(new BoolType(), false, left, right);
        }
        throw new CompilationError("type error occurs in \"<=\"");
    }
}
