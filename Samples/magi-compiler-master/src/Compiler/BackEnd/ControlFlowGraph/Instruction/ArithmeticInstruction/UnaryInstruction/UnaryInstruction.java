package Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.Collections;
import java.util.List;

public abstract class UnaryInstruction extends ArithmeticInstruction {
	public VirtualRegister destination;
	public Operand source;

	protected UnaryInstruction(VirtualRegister destination, Operand source) {
		this.destination = destination;
		this.source = source;
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return Collections.singletonList(destination);
	}

	@Override
	public List<Operand> getUsedOperands() {
		return Collections.singletonList(source);
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
		if (destination == from) {
			destination = to;
		}
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
		if (source == from) {
			source = to;
		}
	}
}
