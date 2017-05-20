package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.BitwiseXorInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/4/17.
 */
public class LogicalNotExpression extends UnaryExpression {
    public LogicalNotExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue, expression);
    }

    public static Expression getExpression(Expression expression) {
        if (expression.type instanceof BoolType) {
            if (expression instanceof BoolConstant) {
                return new BoolConstant(!(((BoolConstant) expression).tag));
            }
            return new BitwiseNotExpression(new BoolType(), false, expression);
        }
        throw new CompilationError("logical-not needs bool.");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        expression.emit(instructions);
        expression.load(instructions);
        operand = Environment.registerTable.addTemporaryRegister();
        instructions.add(BitwiseXorInstruction.getInstruction(operand, expression.operand, new ImmediateValue(1)));
    }
}
