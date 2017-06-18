package Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

import java.util.Collections;
import java.util.List;

public class AllocateInstruction extends MemoryInstruction {
	public VirtualRegister destination;
	public Operand size;

	private AllocateInstruction(VirtualRegister destination, Operand size) {
		this.destination = destination;
		this.size = size;
	}

	public static Instruction getInstruction(Operand target, Operand size) {
		if (target instanceof VirtualRegister) {
			return new AllocateInstruction((VirtualRegister)target, size);
		}
		throw new InternalError();
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return Collections.singletonList(destination);
	}

	@Override
	public List<Operand> getUsedOperands() {
		return Collections.singletonList(size);
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
		if (destination == from) {
			destination = to;
		}
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
		if (size == from) {
			size = to;
		}
	}

	@Override
	public String toString() {
		return destination + " = alloc " + size;
	}
}
