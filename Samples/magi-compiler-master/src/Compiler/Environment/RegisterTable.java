package Compiler.Environment;

import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.StringRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.ParameterRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.SymbolTable.Symbol;

import java.util.HashSet;
import java.util.Set;

public class RegisterTable {
	public Set<VirtualRegister> registers;

	public RegisterTable() {
		registers = new HashSet<>();
	}

	public VirtualRegister addGlobalRegister(Symbol symbol) {
		VirtualRegister register = new GlobalRegister(symbol);
		registers.add(register);
		return register;
	}

	public VirtualRegister addTemporaryRegister(Symbol symbol) {
		VirtualRegister register = new TemporaryRegister(symbol);
		registers.add(register);
		return register;
	}

	public VirtualRegister addTemporaryRegister() {
		return addTemporaryRegister(null);
	}

	public VirtualRegister addParameterRegister(Symbol symbol) {
		VirtualRegister register = new ParameterRegister(symbol);
		registers.add(register);
		return register;
	}

	public VirtualRegister addStringRegister(String literal) {
		VirtualRegister register = new StringRegister(literal);
		registers.add(register);
		return register;
	}
}
