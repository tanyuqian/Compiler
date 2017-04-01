package Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.Collections;
import java.util.List;

public class BranchInstruction extends ControlFlowInstruction {
	public Operand condition;
	public LabelInstruction trueTo, falseTo;

	private BranchInstruction(Operand condition, LabelInstruction trueTo, LabelInstruction falseTo) {
		this.condition = condition;
		this.trueTo = trueTo;
		this.falseTo = falseTo;
	}

	public static Instruction getInstruction(Operand condition, LabelInstruction i1, LabelInstruction i2) {
		return new BranchInstruction(condition, i1, i2).rebuild();
	}

	@Override
	public Instruction rebuild() {
		if (condition instanceof ImmediateValue) {
			int literal = ((ImmediateValue)condition).literal;
			if (literal == 0) {
				return JumpInstruction.getInstruction(falseTo);
			} else {
				return JumpInstruction.getInstruction(trueTo);
			}
		}
		return this;
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return Collections.emptyList();
	}

	@Override
	public List<Operand> getUsedOperands() {
		return Collections.singletonList(condition);
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
		if (condition == from) {
			condition = to;
		}
	}

	@Override
	public String toString() {
		return "br " + condition + " " + trueTo.block + " " + falseTo.block;
	}
}
