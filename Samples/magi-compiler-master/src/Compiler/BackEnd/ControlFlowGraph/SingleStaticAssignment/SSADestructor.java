package Compiler.BackEnd.ControlFlowGraph.SingleStaticAssignment;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.PhiFunctionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.CloneRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.SymbolTable.Symbol;

public class SSADestructor {
	private Graph graph;

	public SSADestructor(Graph graph) {
		this.graph = graph.refresh();
		removePhiFunctions();
		renameRegisters();
	}

	private void removePhiFunctions() {
		for (Block block : graph.blocks) {
			for (int k = 0; k < block.phiFunctions.size(); ++k) {
				PhiFunctionInstruction i = (PhiFunctionInstruction)block.phiFunctions.get(k);
				for (Block predecessor : block.predecessors) {
					if (i.sources.containsKey(predecessor)) {
						predecessor.instructions.add(predecessor.instructions.size() - 1, MoveInstruction.getInstruction(i.destination, i.sources.get(predecessor)));
					}
				}
				block.phiFunctions.set(k, null);
			}
		}
		graph.refresh();
	}

	private void renameRegisters() {
		for (Symbol parameter : graph.function.parameters) {
			parameter.register = rename(parameter.register);
		}
		for (Block block : graph.blocks) {
			for (Instruction instruction : block.instructions) {
				for (VirtualRegister register : instruction.getDefinedRegisters()) {
					instruction.setDefinedRegister(register, rename(register));
				}
				for (VirtualRegister register : instruction.getUsedRegisters()) {
					instruction.setUsedRegister(register, rename(register));
				}
			}
		}
	}

	private VirtualRegister rename(VirtualRegister register) {
		if (register instanceof CloneRegister) {
			CloneRegister clone = (CloneRegister)register;
			return clone.origin;
		} else {
			return register;
		}
	}
}
