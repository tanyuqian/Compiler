package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.BitwiseAndInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.BitwiseXorInstruction;
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
public class BitwiseXorExpression extends BinaryExpression {
    public BitwiseXorExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if ((left.type instanceof IntType) && (right.type instanceof IntType)) {
            if ((left instanceof IntConstant) && (right instanceof IntConstant)) {
                int a = ((IntConstant) left).number, b = ((IntConstant) right).number;
                return new IntConstant(a ^ b);
            }
            return new BitwiseXorExpression(new IntType(), false, left, right);
        }
        throw new CompilationError("bitwise-xor needs two number of IntType.");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        left.emit(instructions);
        left.load(instructions);
        right.emit(instructions);
        right.load(instructions);
        operand = Environment.registerTable.addTemporaryRegister();
        instructions.add(BitwiseXorInstruction.getInstruction(operand, left.operand, right.operand));
    }
}
