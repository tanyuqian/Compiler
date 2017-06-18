package Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

public class BitwiseNotInstruction extends UnaryInstruction {
	private BitwiseNotInstruction(VirtualRegister destination, Operand source) {
		super(destination, source);
	}

	public static Instruction getInstruction(Operand destination, Operand source) {
		return new BitwiseNotInstruction((VirtualRegister)destination, source).rebuild();
	}

	@Override
	public Instruction rebuild() {
		if (source instanceof ImmediateValue) {
			int literal = ((ImmediateValue)source).literal;
			return MoveInstruction.getInstruction(destination, new ImmediateValue(~literal));
		}
		return this;
	}

	@Override
	public String MIPSName() {
		return "not";
	}

	@Override
	public String toString() {
		return String.format("%s = not %s", destination, source);
	}
}
