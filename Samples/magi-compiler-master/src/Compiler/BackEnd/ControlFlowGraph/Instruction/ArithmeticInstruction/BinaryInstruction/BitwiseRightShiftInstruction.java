package Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

public class BitwiseRightShiftInstruction extends BinaryInstruction {
	private BitwiseRightShiftInstruction(VirtualRegister target, Operand source1, Operand source2) {
		super(target, source1, source2);
	}

	public static Instruction getInstruction(Operand target, Operand source1, Operand source2) {
		if (target instanceof VirtualRegister) {
			return new BitwiseRightShiftInstruction((VirtualRegister)target, source1, source2).rebuild();
		}
		throw new InternalError();
	}

	@Override
	public Instruction rebuild() {
		return this;
	}

	@Override
	public String MIPSName() {
		return "sra";
	}

	@Override
	public String toString() {
		return String.format("%s = shr %s %s", destination, source1, source2);
	}
}
