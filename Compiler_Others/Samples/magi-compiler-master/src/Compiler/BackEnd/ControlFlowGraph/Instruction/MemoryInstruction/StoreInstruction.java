package Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Address;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StoreInstruction extends MemoryInstruction {
	public Operand source;
	public Address address;

	private StoreInstruction(Operand source, Address address) {
		this.source = source;
		this.address = address;
	}

	public static Instruction getInstruction(Operand source, Operand address) {
		if (address instanceof Address) {
			return new StoreInstruction(source, (Address)address);
		}
		throw new InternalError();
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return Collections.emptyList();
	}

	@Override
	public List<Operand> getUsedOperands() {
		return Arrays.asList(source, address.base);
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
		if (source == from) {
			source = to;
		}
		if (address.base == from) {
			address = new Address((VirtualRegister)to, address.offset, address.size);
		}
	}

	public String MIPSName() {
		if (address.size == 1) {
			return "sb";
		} else if (address.size == 4) {
			return "sw";
		}
		throw new InternalError();
	}

	@Override
	public String toString() {
		return "store " + address.size + " " + address.base + " " + source + " " + address.offset;
	}
}
