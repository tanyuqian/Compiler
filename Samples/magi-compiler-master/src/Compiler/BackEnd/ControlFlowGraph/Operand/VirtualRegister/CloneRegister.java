package Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister;

public class CloneRegister extends VirtualRegister {
	public VirtualRegister origin;
	private int version;

	public CloneRegister(VirtualRegister origin, int version) {
		this.origin = origin;
		this.version = version;
	}

	@Override
	public String toString() {
		return String.format("%s.%d", origin, version);
	}
}
