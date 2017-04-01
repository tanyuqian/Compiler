package Compiler.BackEnd.ControlFlowGraph.Instruction;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.*;

public class PhiFunctionInstruction extends Instruction {
	public VirtualRegister destination;
	public Map<Block, Operand> sources;

	private PhiFunctionInstruction(VirtualRegister destination, List<Block> predecessors) {
		this.destination = destination;
		this.sources = new HashMap<Block, Operand>() {{
			for (int i = 0; i < predecessors.size(); ++i) {
				put(predecessors.get(i), null);
			}
		}};
	}

	public static Instruction getInstruction(VirtualRegister destination, List<Block> predecessors) {
		return new PhiFunctionInstruction(destination, predecessors);
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return Collections.singletonList(destination);
	}

	@Override
	public List<Operand> getUsedOperands() {
		return new ArrayList<Operand>() {{
			for (Block block : sources.keySet()) {
				Operand operand = sources.get(block);
				if (operand != null) {
					add(operand);
				}
			}
		}};
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
		if (destination == from) {
			destination = to;
		}
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
		for (Block block : sources.keySet()) {
			Operand operand = sources.get(block);
			if (operand == from) {
				sources.put(block, to);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(destination).append(" = phi");
		for (Block block : sources.keySet()) {
			stringBuilder.append(" ").append(block).append(" ");
			Operand operand = sources.get(block);
			if (operand == null) {
				stringBuilder.append("undef");
			} else {
				stringBuilder.append(operand);
			}
		}
		return stringBuilder.toString();
	}
}
