package Compiler.FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.LoadInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.StoreInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Address;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.List;

public class AssignmentExpression extends BinaryExpression {
	private AssignmentExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
		super(type, isLeftValue, left, right);
	}

	public static Expression getExpression(Expression left, Expression right) {
		if (!left.isLeftValue) {
			throw new CompilationError("a left-value expression is expected in the left-hand-side of the assignment expression");
		}
		if (left.type.compatibleWith(right.type)) {
			return new AssignmentExpression(left.type, true, left, right);
		}
		throw new CompilationError("two expressions with compatible types are expected in the assignment expression");
	}

	@Override
	public String toString() {
		return "[expression: assignment]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		left.emit(instructions);
		right.emit(instructions);
		right.load(instructions);
		operand = left.operand;
		if (left.operand instanceof Address) {
			instructions.add(StoreInstruction.getInstruction(right.operand, left.operand));
		} else {
			instructions.add(MoveInstruction.getInstruction(left.operand, right.operand));
		}
	}

	@Override
	public void load(List<Instruction> instructions) {
		if (operand instanceof Address) {
			Address address = (Address)operand;
			operand = Environment.registerTable.addTemporaryRegister();
			instructions.add(LoadInstruction.getInstruction(operand, address));
		}
	}
}
