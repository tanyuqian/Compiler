package Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

import java.util.Collections;
import java.util.List;

public class MoveInstruction extends MemoryInstruction {
	public VirtualRegister destination;
	public Operand source;

	private MoveInstruction(VirtualRegister destination, Operand source) {
		this.destination = destination;
		this.source = source;
	}

	public static Instruction getInstruction(Operand target, Operand source) {
		if (target instanceof VirtualRegister) {
			return new MoveInstruction((VirtualRegister)target, source);
		}
		throw new InternalError();
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

	@Override
	public String toString() {
		return destination + " = move " + source;
	}
}
