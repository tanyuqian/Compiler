package Compiler.FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.BitwiseLeftShiftInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.List;

public class BitwiseLeftShiftExpression extends BinaryExpression {
	private BitwiseLeftShiftExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
		super(type, isLeftValue, left, right);
	}

	public static Expression getExpression(Expression left, Expression right) {
		if (left.type instanceof IntType && right.type instanceof IntType) {
			if (left instanceof IntConstant && right instanceof IntConstant) {
				int literal1 = ((IntConstant)left).literal;
				int literal2 = ((IntConstant)right).literal;
				return IntConstant.getConstant(literal1 << literal2);
			}
			return new BitwiseLeftShiftExpression(IntType.getType(), false, left, right);
		}
		throw new CompilationError("two int-type expressions are expected in the bitwise-left-shift expression");
	}

	@Override
	public String toString() {
		return "[expression: bitwise-left-shift]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		left.emit(instructions);
		left.load(instructions);
		right.emit(instructions);
		right.load(instructions);
		operand = Environment.registerTable.addTemporaryRegister();
		instructions.add(BitwiseLeftShiftInstruction.getInstruction(operand, left.operand, right.operand));
	}
}
