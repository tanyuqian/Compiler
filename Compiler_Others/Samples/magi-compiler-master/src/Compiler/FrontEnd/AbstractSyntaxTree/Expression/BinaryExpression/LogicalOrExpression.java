package Compiler.FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.List;

public class LogicalOrExpression extends BinaryExpression {
	private LogicalOrExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
		super(type, isLeftValue, left, right);
	}

	public static Expression getExpression(Expression left, Expression right) {
		if (left.type instanceof BoolType && right.type instanceof BoolType) {
			if (left instanceof BoolConstant && right instanceof BoolConstant) {
				boolean literal1 = ((BoolConstant)left).literal;
				boolean literal2 = ((BoolConstant)right).literal;
				return BoolConstant.getConstant(literal1 || literal2);
			}
			return new LogicalOrExpression(BoolType.getType(), false, left, right);
		}
		throw new CompilationError("two bool-type expressions are expected in the logical-or expression");
	}

	@Override
	public String toString() {
		return "[expression: logical-or]";
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
			%logical_false:
				****right****
				$operand = move $right
				goto %logical_merge
			%logical_true:
				$operand = move 1
				goto %logical_merge
			%logical_merge:
				...
		 */
		operand = Environment.registerTable.addTemporaryRegister();
		left.emit(instructions);
		left.load(instructions);
		instructions.add(BranchInstruction.getInstruction(left.operand, trueLabel, falseLabel));
		instructions.add(falseLabel);
		right.emit(instructions);
		right.load(instructions);
		operand = right.operand;
		instructions.add(JumpInstruction.getInstruction(mergeLabel));
		instructions.add(trueLabel);
		instructions.add(MoveInstruction.getInstruction(operand, new ImmediateValue(1)));
		instructions.add(JumpInstruction.getInstruction(mergeLabel));
		instructions.add(mergeLabel);
	}
}
