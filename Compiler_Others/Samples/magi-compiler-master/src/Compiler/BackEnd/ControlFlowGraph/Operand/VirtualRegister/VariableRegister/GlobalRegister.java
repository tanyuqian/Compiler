package Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister;

import Compiler.Environment.SymbolTable.Symbol;

public class GlobalRegister extends VariableRegister {
	public GlobalRegister(Symbol symbol) {
		super(symbol);
	}

	@Override
	public String toString() {
		return String.format("$g%s(%s)", identity, symbol.name);
	}
}
