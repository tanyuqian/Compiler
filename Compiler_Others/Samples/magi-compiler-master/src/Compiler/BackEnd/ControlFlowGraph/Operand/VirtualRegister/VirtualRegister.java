package Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister;

import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.Environment.Environment;

import java.util.ArrayList;
import java.util.List;

public abstract class VirtualRegister extends Operand {
	public int identity;
	private List<VirtualRegister> clones;

	public VirtualRegister() {
		this.identity = Environment.registerTable.registers.size();
		this.clones = new ArrayList<>();
	}

	public VirtualRegister clone() {
		VirtualRegister clone = new CloneRegister(this, clones.size());
		clones.add(clone);
		return clone;
	}

	@Override
	public String toString() {
		return String.format("$%d", identity);
	}
}
