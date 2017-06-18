package Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.Collections;
import java.util.List;

public class JumpInstruction extends ControlFlowInstruction {
	public LabelInstruction to;

	private JumpInstruction(LabelInstruction to) {
		this.to = to;
	}

	public static Instruction getInstruction(LabelInstruction toLabel) {
		return new JumpInstruction(toLabel);
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
		return "jump " + to.block;
	}
}
