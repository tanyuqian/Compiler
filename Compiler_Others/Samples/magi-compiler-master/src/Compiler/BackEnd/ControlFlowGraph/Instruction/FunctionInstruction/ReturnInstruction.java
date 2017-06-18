package Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.Collections;
import java.util.List;

public class ReturnInstruction extends FunctionInstruction {
	public Operand source;

	private ReturnInstruction(Operand source) {
		this.source = source;
	}

	public static Instruction getInstruction(Operand source) {
		return new ReturnInstruction(source);
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return Collections.emptyList();
	}

	@Override
	public List<Operand> getUsedOperands() {
		return Collections.singletonList(source);
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
		if (source == from) {
			source = to;
		}
	}

	@Override
	public String toString() {
		return "ret " + source;
	}
}
