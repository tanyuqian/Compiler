package Compiler.FrontEnd.AbstractSyntaxTree.Expression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.FrontEnd.AbstractSyntaxTree.Node;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

public abstract class Expression implements Node {
	public Type type;
	public boolean isLeftValue;
	public Operand operand;

	protected Expression(Type type, boolean isLeftValue) {
		this.type = type;
		this.isLeftValue = isLeftValue;
		this.operand = null;
	}

	public abstract void emit(List<Instruction> instructions);

	public void load(List<Instruction> instructions) {
	}
}
