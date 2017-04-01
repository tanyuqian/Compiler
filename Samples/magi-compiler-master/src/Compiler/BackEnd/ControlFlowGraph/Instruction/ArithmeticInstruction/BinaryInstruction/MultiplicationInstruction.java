package Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

public class MultiplicationInstruction extends BinaryInstruction {
	private MultiplicationInstruction(VirtualRegister destination, Operand source1, Operand source2) {
		super(destination, source1, source2);
	}

	public static Instruction getInstruction(Operand destination, Operand source1, Operand source2) {
		if (destination instanceof VirtualRegister) {
			return new MultiplicationInstruction((VirtualRegister)destination, source1, source2).rebuild();
		}
		throw new InternalError();
	}

	@Override
	public Instruction rebuild() {
		if (source1 instanceof ImmediateValue && source2 instanceof ImmediateValue) {
			int literal1 = ((ImmediateValue)source1).literal;
			int literal2 = ((ImmediateValue)source2).literal;
			return MoveInstruction.getInstruction(destination, new ImmediateValue(literal1 * literal2));
		}
		if (source1 instanceof ImmediateValue) {
			Operand swap = source1;
			source1 = source2;
			source2 = swap;
		}
		return this;
	}

	@Override
	public String MIPSName() {
		return "mul";
	}

	@Override
	public String toString() {
		return String.format("%s = mul %s %s", destination, source1, source2);
	}
}
