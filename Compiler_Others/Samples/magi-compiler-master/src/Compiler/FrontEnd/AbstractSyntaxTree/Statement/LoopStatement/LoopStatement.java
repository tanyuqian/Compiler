package Compiler.FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import Compiler.BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Compiler.Environment.ScopeTable.Scope;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.Statement;

public abstract class LoopStatement extends Statement implements Scope {
	public LabelInstruction loop, merge;
}
