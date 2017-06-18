package Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

public class LessThanOrEqualToInstruction extends BinaryInstruction {
	private LessThanOrEqualToInstruction(VirtualRegister target, Operand source1, Operand source2) {
		super(target, source1, source2);
	}

	public static Instruction getInstruction(Operand target, Operand source1, Operand source2) {
		if (target instanceof VirtualRegister) {
			return new LessThanOrEqualToInstruction((VirtualRegister)target, source1, source2).rebuild();
		}
		throw new InternalError();
	}

	@Override
	public Instruction rebuild() {
		if (source1 instanceof ImmediateValue && source2 instanceof ImmediateValue) {
			int literal1 = ((ImmediateValue)source1).literal;
			int literal2 = ((ImmediateValue)source2).literal;
			return MoveInstruction.getInstruction(destination, new ImmediateValue(literal1 <= literal2 ? 1 : 0));
		}
		return this;
	}

	@Override
	public String MIPSName() {
		return "sle";
	}

	@Override
	public String toString() {
		return String.format("%s = sle %s %s", destination, source1, source2);
	}
}
