package Compiler.FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.List;

public class LogicalAndExpression extends BinaryExpression {
	private LogicalAndExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
		super(type, isLeftValue, left, right);
	}

	public static Expression getExpression(Expression left, Expression right) {
		if (left.type instanceof BoolType && right.type instanceof BoolType) {
			if (left instanceof BoolConstant && right instanceof BoolConstant) {
				boolean literal1 = ((BoolConstant)left).literal;
				boolean literal2 = ((BoolConstant)right).literal;
				return BoolConstant.getConstant(literal1 && literal2);
			}
			return new LogicalAndExpression(BoolType.getType(), false, left, right);
		}
		throw new CompilationError("two bool-type expressions are expected in the logical-and expression");
	}

	@Override
	public String toString() {
		return "[expression: logical-and]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		LabelInstruction trueLabel = LabelInstruction.getInstruction("logical_true");
		LabelInstruction falseLabel = LabelInstruction.getInstruction("logical_false");
		LabelInstruction mergeLabel = LabelInstruction.getInstruction("logical_merge");
		/*
			%...:
				****left****
				branch $left, %logical_true, %logical_false
			%logical_true:
				****right****
				$operand = move $right
				goto logical_merge
			%logical_false:
				$operand = move 0
				goto logical_merge
			%logical_merge:
				...
		 */
		left.emit(instructions);
		left.load(instructions);
		instructions.add(BranchInstruction.getInstruction(left.operand, trueLabel, falseLabel));
		instructions.add(trueLabel);
		right.emit(instructions);
		right.load(instructions);
		operand = right.operand;
		instructions.add(JumpInstruction.getInstruction(mergeLabel));
		instructions.add(falseLabel);
		instructions.add(MoveInstruction.getInstruction(operand, new ImmediateValue(0)));
		instructions.add(JumpInstruction.getInstruction(mergeLabel));
		instructions.add(mergeLabel);
	}
}
