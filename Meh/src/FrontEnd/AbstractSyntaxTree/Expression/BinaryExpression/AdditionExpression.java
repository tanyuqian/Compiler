package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.StringConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Expression.FunctionCallExpression;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.ArrayList;

/**
 * Created by tan on 4/3/17.
 */
public class AdditionExpression extends BinaryExpression {
    public AdditionExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if ((left.type instanceof IntType) && (right.type instanceof IntType)) {
            if ((left instanceof IntConstant) && (right instanceof IntConstant)) {
                int a = ((IntConstant)left).number, b = ((IntConstant)right).number;
                return new IntConstant(a + b);
            }
            return new AdditionExpression(new IntType(), false, left, right);
        } else if ((left.type instanceof StringType) && (right.type instanceof StringType)) {
            if ((left instanceof StringConstant) && (right instanceof StringConstant)) {
                String strLeft = ((StringConstant) left).str;
                String strRight = ((StringConstant) right).str;
                return new StringConstant(strLeft + strRight);
            }
            return new AdditionExpression(new StringType(), false, left, right);
        }
        throw new CompilationError("type Error beside an \"+\"");
    }
}
