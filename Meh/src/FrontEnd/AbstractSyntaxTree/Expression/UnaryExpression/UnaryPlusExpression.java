package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/4/17.
 */
public class UnaryPlusExpression extends UnaryMinusExpression {
    public UnaryPlusExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue, expression);
    }

    public static Expression getExpression(Expression expression) {
        if (expression.type instanceof IntType) {
            if (expression instanceof IntConstant) {
                return new IntConstant(+((IntConstant) expression).number);
            }
            return new UnaryPlusExpression(new IntType(), false, expression);
        }
        throw new CompilationError("unary-plus: type error.");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        operand = expression.operand;
    }
}
