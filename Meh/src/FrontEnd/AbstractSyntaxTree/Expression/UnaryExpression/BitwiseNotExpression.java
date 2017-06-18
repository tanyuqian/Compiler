package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.BitwiseNotInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/4/17.
 */
public class BitwiseNotExpression extends UnaryExpression {
    public BitwiseNotExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue, expression);
    }

    public static Expression getExpression(Expression expression) {
        if (expression.type instanceof IntType) {
            if (expression instanceof IntConstant) {
                return new IntConstant(~(((IntConstant) expression).number));
            }
            return new BitwiseNotExpression(new IntType(), false, expression);
        }
        throw new CompilationError("bitwise-not needs int.");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        expression.emit(instructions);
        expression.load(instructions);
        operand = Environment.registerTable.addTemporaryRegister();
        instructions.add(BitwiseNotInstruction.getInstruction(operand, expression.operand));
    }
}
