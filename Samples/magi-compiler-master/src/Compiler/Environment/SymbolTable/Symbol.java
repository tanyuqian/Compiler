package Compiler.Environment.SymbolTable;

import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.Environment;
import Compiler.Environment.ScopeTable.Scope;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;

public class Symbol {
	public String name;
	public Type type;
	public Scope scope;
	public VirtualRegister register;

	public Symbol(String name, Type type) {
		this.name = name;
		this.type = type;
		this.scope = Environment.scopeTable.getScope();
		this.register = null;
	}
}
