package Compiler.BackEnd.ControlFlowGraph.Operand;

import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

public class Address extends Operand {
	public VirtualRegister base;
	public ImmediateValue offset;
	public int size;

	public Address(VirtualRegister base, ImmediateValue offset, int size) {
		if (size != 1 && size != 4) {
			throw new InternalError();
		}
		this.base = base;
		this.offset = offset;
		this.size = size;
	}

	public Address(VirtualRegister base, int size) {
		if (size != 1 && size != 4) {
			throw new InternalError();
		}
		this.base = base;
		this.offset = new ImmediateValue(0);
		this.size = size;
	}

	@Override
	public String toString() {
		return String.format("address(%s + %s, %d)", base, offset, size);
	}
}
