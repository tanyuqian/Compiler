package Compiler.FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.SubtractionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.StoreInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Address;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.List;

public class PrefixDecrementExpression extends UnaryExpression {
	private PrefixDecrementExpression(Type type, boolean isLeftValue, Expression expression) {
		super(type, isLeftValue, expression);
	}

	public static Expression getExpression(Expression expression) {
		if (!expression.isLeftValue) {
			throw new CompilationError("a left-value expression is expected in the prefix-decrement expression");
		}
		if (expression.type instanceof IntType) {
			return new PrefixDecrementExpression(IntType.getType(), false, expression);
		}
		throw new CompilationError("an int-type expression is expected in the prefix-decrement expression");
	}

	@Override
	public String toString() {
		return "[expression: prefix-decrement]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		expression.emit(instructions);
		if (expression.operand instanceof Address) {
			Address address = (Address)expression.operand;
			address = new Address(address.base, address.offset, address.size);
			expression.load(instructions);
			operand = expression.operand;
			instructions.add(SubtractionInstruction.getInstruction(operand, operand, new ImmediateValue(1)));
			instructions.add(StoreInstruction.getInstruction(operand, address));
		} else {
			expression.load(instructions);
			operand = expression.operand;
			instructions.add(SubtractionInstruction.getInstruction(operand, operand, new ImmediateValue(1)));
		}
	}
}
