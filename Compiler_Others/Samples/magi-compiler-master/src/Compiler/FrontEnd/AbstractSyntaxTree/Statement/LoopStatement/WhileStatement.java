package Compiler.FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.Statement;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Utility;

import java.util.List;

public class WhileStatement extends LoopStatement {
	public Expression condition;
	public Statement statement;

	public static Statement getStatement() {
		return new WhileStatement();
	}

	public void addCondition(Expression condition) {
		if (!(condition.type instanceof BoolType)) {
			throw new CompilationError("a bool-type condition is expected in the while statement");
		}
		this.condition = condition;
	}

	public void addStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public String toString() {
		return "[statement: while]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(toString()).append("\n");
		stringBuilder.append(condition.toString(indents + 1));
		stringBuilder.append(statement.toString(indents + 1));
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		LabelInstruction bodyLabel = LabelInstruction.getInstruction("while_body");
		loop = LabelInstruction.getInstruction("while_loop");
		merge = LabelInstruction.getInstruction("while_merge");
		/*
			%...:
				jump %while_loop
			%while_loop:
				****condition****
				branch $condition, %while_body, %while_merge
			%while_body:
				****statement****
				jump %while_loop
			%while_merge:
				...
		 */
		instructions.add(JumpInstruction.getInstruction(loop));
		instructions.add(loop);
		condition.emit(instructions);
		instructions.add(BranchInstruction.getInstruction(condition.operand, bodyLabel, merge));
		instructions.add(bodyLabel);
		statement.emit(instructions);
		instructions.add(JumpInstruction.getInstruction(loop));
		instructions.add(merge);
	}
}
