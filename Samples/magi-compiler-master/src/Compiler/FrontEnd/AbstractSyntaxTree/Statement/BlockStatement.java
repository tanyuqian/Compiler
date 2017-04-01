package Compiler.FrontEnd.AbstractSyntaxTree.Statement;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.ScopeTable.Scope;
import Compiler.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement extends Statement implements Scope {
	public ArrayList<Statement> statements;

	private BlockStatement() {
		statements = new ArrayList<>();
	}

	public static Statement getStatement() {
		return new BlockStatement();
	}

	public void addStatement(Statement statement) {
		statements.add(statement);
	}

	@Override
	public String toString() {
		return "[block-statement]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(toString()).append("\n");
		statements.forEach(statement -> stringBuilder.append(statement.toString(indents + 1)));
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		statements.forEach(statement -> statement.emit(instructions));
	}
}
