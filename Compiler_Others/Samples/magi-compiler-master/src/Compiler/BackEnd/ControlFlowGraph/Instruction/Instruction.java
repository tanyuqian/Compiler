package Compiler.BackEnd.ControlFlowGraph.Instruction;

import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public abstract class Instruction {
	public Instruction rebuild() {
		return this;
	}

	public abstract List<Operand> getDefinedOperands();
	public abstract List<Operand> getUsedOperands();

	public List<VirtualRegister> getDefinedRegisters() {
		return new ArrayList<VirtualRegister>() {{
			for (Operand operand : getDefinedOperands()) {
				if (operand instanceof VirtualRegister) {
					add((VirtualRegister)operand);
				}
			}
		}};
	}

	public List<VirtualRegister> getUsedRegisters() {
		return new ArrayList<VirtualRegister>() {{
			for (Operand operand : getUsedOperands()) {
				if (operand instanceof VirtualRegister) {
					add((VirtualRegister)operand);
				}
			}
		}};
	}

	public abstract void setDefinedRegister(VirtualRegister from, VirtualRegister to);
	public abstract void setUsedRegister(VirtualRegister from, Operand to);

	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(this).append("\n");
		return stringBuilder.toString();
	}
}
