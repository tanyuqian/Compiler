package Compiler.FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.BitwiseXorInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.List;

public class LogicalNotExpression extends UnaryExpression {
	private LogicalNotExpression(Type type, boolean isLeftValue, Expression expression) {
		super(type, isLeftValue, expression);
	}

	public static Expression getExpression(Expression expression) {
		if (expression.type instanceof BoolType) {
			if (expression instanceof BoolConstant) {
				boolean literal = ((BoolConstant)expression).literal;
				return BoolConstant.getConstant(!literal);
			}
			return new LogicalNotExpression(BoolType.getType(), false, expression);
		}
		throw new CompilationError("a bool-type expression is expected in the logical-not expression");
	}

	@Override
	public String toString() {
		return "[expression: logical-not]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		expression.emit(instructions);
		expression.load(instructions);
		operand = Environment.registerTable.addTemporaryRegister();
		instructions.add(BitwiseXorInstruction.getInstruction(operand, expression.operand, new ImmediateValue(1)));
	}
}