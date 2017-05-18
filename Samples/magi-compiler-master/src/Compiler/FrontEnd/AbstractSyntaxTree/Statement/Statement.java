package Compiler.FrontEnd.AbstractSyntaxTree.Statement;

		import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
		import Compiler.FrontEnd.AbstractSyntaxTree.Node;

		import java.util.List;

public abstract class Statement implements Node {
	public abstract void emit(List<Instruction> instructions);
}
