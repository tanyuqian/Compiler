package Compiler.FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.BitwiseNotInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.List;

public class BitwiseNotExpression extends UnaryExpression {
	private BitwiseNotExpression(Type type, boolean isLeftValue, Expression expression) {
		super(type, isLeftValue, expression);
	}

	public static Expression getExpression(Expression expression) {
		if (expression.type instanceof IntType) {
			if (expression instanceof IntConstant) {
				int literal = ((IntConstant)expression).literal;
				return IntConstant.getConstant(~literal);
			}
			return new BitwiseNotExpression(IntType.getType(), false, expression);
		}
		throw new CompilationError("an int-type expression is expected in the bitwise-not expression");
	}

	@Override
	public String toString() {
		return "[expression: bitwise-not]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		expression.emit(instructions);
		expression.load(instructions);
		operand = Environment.registerTable.addTemporaryRegister();
		instructions.add(BitwiseNotInstruction.getInstruction(operand, expression.operand));
	}
}
