package Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

public class AdditionInstruction extends BinaryInstruction {
	private AdditionInstruction(VirtualRegister target, Operand source1, Operand source2) {
		super(target, source1, source2);
	}

	public static Instruction getInstruction(Operand target, Operand source1, Operand source2) {
		if (target instanceof VirtualRegister) {
			return new AdditionInstruction((VirtualRegister)target, source1, source2).rebuild();
		}
		throw new InternalError();
	}

	@Override
	public Instruction rebuild() {
		if (source1 instanceof ImmediateValue && source2 instanceof ImmediateValue) {
			int literal1 = ((ImmediateValue)source1).literal;
			int literal2 = ((ImmediateValue)source2).literal;
			return MoveInstruction.getInstruction(destination, new ImmediateValue(literal1 + literal2));
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
		return "addu";
	}

	@Override
	public String toString() {
		return String.format("%s = add %s %s", destination, source1, source2);
	}
}
