package Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister;

public class StringRegister extends VirtualRegister {
	public String literal;

	public StringRegister(String literal) {
		this.literal = literal;
	}
}
