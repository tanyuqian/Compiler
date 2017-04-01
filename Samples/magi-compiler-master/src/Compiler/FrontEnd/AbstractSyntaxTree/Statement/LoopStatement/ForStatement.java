package Compiler.FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.Statement;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Utility;

import java.util.List;

public class ForStatement extends LoopStatement {
	public Expression initialization, condition, increment;
	public Statement statement;

	public static Statement getStatement() {
		return new ForStatement();
	}

	public void addInitialization(Expression initialization) {
		this.initialization = initialization;
	}

	public void addCondition(Expression condition) {
		if (condition == null) {
			this.condition = BoolConstant.getConstant(true);
			return;
		}
		if (condition.type instanceof BoolType) {
			this.condition = condition;
			return;
		}
		throw new CompilationError("a bool-type condition is expected in the for statement");
	}

	public void addIncrement(Expression increment) {
		this.increment = increment;
	}

	public void addStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public String toString() {
		return "[statement: for]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(toString()).append("\n");
		if (initialization != null) {
			stringBuilder.append(initialization.toString(indents + 1));
		}
		stringBuilder.append(condition.toString(indents + 1));
		if (increment != null) {
			stringBuilder.append(increment.toString(indents + 1));
		}
		stringBuilder.append(statement.toString(indents + 1));
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		LabelInstruction conditionLabel = LabelInstruction.getInstruction("for_condition");
		LabelInstruction bodyLabel = LabelInstruction.getInstruction("for_body");
		loop = LabelInstruction.getInstruction("for_loop");
		merge = LabelInstruction.getInstruction("for_merge");
		/*
			%...:
				****initialization****
				jump %condition
			%for_condition:
				****condition****
				branch $condition, %for_body, %for_merge
			%for_body:
				****statement****
				jump %for_loop
			%for_loop:
				****increment****
				jump %for_condition
			%for_merge:
				...
		 */
		if (initialization != null) {
			initialization.emit(instructions);
		}
		instructions.add(JumpInstruction.getInstruction(conditionLabel));
		instructions.add(conditionLabel);
		if (condition == null) {
			addCondition(null);
		}
		condition.emit(instructions);
		instructions.add(BranchInstruction.getInstruction(condition.operand, bodyLabel, merge));
		instructions.add(bodyLabel);
		if (statement != null) {
			statement.emit(instructions);
		}
		instructions.add(JumpInstruction.getInstruction(loop));
		instructions.add(loop);
		if (increment != null) {
			increment.emit(instructions);
		}
		instructions.add(JumpInstruction.getInstruction(conditionLabel));
		instructions.add(merge);
	}
}
