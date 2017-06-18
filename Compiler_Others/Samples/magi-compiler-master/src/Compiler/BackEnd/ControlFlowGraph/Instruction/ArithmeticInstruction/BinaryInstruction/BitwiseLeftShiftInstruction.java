package Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

public class BitwiseLeftShiftInstruction extends BinaryInstruction {
	private BitwiseLeftShiftInstruction(VirtualRegister target, Operand source1, Operand source2) {
		super(target, source1, source2);
	}

	public static Instruction getInstruction(Operand target, Operand source1, Operand source2) {
		if (target instanceof VirtualRegister) {
			return new BitwiseLeftShiftInstruction((VirtualRegister)target, source1, source2).rebuild();
		}
		throw new InternalError();
	}

	@Override
	public Instruction rebuild() {
		return this;
	}

	@Override
	public String MIPSName() {
		return "sll";
	}

	@Override
	public String toString() {
		return String.format("%s = shl %s %s", destination, source1, source2);
	}
}
