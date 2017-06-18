package Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister;

import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.SymbolTable.Symbol;

public class VariableRegister extends VirtualRegister {
	public Symbol symbol;

	protected VariableRegister(Symbol symbol) {
		this.symbol = symbol;
	}
}
