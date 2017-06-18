package Compiler.FrontEnd.AbstractSyntaxTree.Statement;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.LoopStatement;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Utility;

import java.util.List;

public class ContinueStatement extends Statement {
	public LoopStatement to;

	private ContinueStatement(LoopStatement to) {
		this.to = to;
	}

	public static Statement getStatement() {
		if (Environment.scopeTable.getLoopScope() == null) {
			throw new CompilationError("the continue statement should be placed in the loop statement");
		}
		return new ContinueStatement(Environment.scopeTable.getLoopScope());
	}

	@Override
	public String toString() {
		return "[statement: continue]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(toString()).append("\n");
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		instructions.add(JumpInstruction.getInstruction(to.loop));
	}
}
