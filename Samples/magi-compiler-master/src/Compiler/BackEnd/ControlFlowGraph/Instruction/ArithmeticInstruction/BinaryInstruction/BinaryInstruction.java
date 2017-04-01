package Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BinaryInstruction extends ArithmeticInstruction {
	public VirtualRegister destination;
	public Operand source1, source2;

	protected BinaryInstruction(VirtualRegister destination, Operand source1, Operand source2) {
		this.destination = destination;
		this.source1 = source1;
		this.source2 = source2;
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return Collections.singletonList(destination);
	}

	@Override
	public List<Operand> getUsedOperands() {
		return Arrays.asList(source1, source2);
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
		if (destination == from) {
			destination = to;
		}
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
		if (source1 == from) {
			source1 = to;
		}
		if (source2 == from) {
			source2 = to;
		}
	}
}
