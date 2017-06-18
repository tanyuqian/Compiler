package Compiler.BackEnd.ControlFlowGraph.Instruction;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.Collections;
import java.util.List;

public class LabelInstruction extends Instruction {
	public String name;
	public Block block;

	private LabelInstruction(String name) {
		this.name = name;
	}

	public static LabelInstruction getInstruction(String name) {
		return new LabelInstruction(name);
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return Collections.emptyList();
	}

	@Override
	public List<Operand> getUsedOperands() {
		return Collections.emptyList();
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
	}

	@Override
	public String toString() {
		return "%" + name;
	}
}

