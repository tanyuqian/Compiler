package Compiler.BackEnd.ControlFlowGraph.SingleStaticAssignment.SSAConstructor;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.PhiFunctionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.*;

class PhiFunctionPlacer {
	private Graph graph;

	PhiFunctionPlacer(Graph graph) {
		this.graph = graph.refresh();
		this.placePhiFunctions();
	}

	private void placePhiFunctions() {
		Set<VirtualRegister> registers = new HashSet<>();
		Map<VirtualRegister, Set<Block>> blocks = new HashMap<>();
		for (Block block : graph.blocks) {
			Set<VirtualRegister> defined = new HashSet<>();
			for (Instruction instruction : block.instructions) {
				for (VirtualRegister register : instruction.getUsedRegisters()) {
					if (!defined.contains(register)) {
						registers.add(register);
					}
				}
				for (VirtualRegister register : instruction.getDefinedRegisters()) {
					defined.add(register);
					if (!blocks.containsKey(register)) {
						blocks.put(register, new HashSet<>());
					}
					blocks.get(register).add(block);
				}
			}
		}
		for (VirtualRegister register : registers) {
			if (blocks.get(register) == null) {
				continue;
			}
			List<Block> list = new ArrayList<>(blocks.get(register));
			for (int k = 0; k < list.size(); ++k) {
				Block block = list.get(k);
				for (Block frontier : block.dominance.frontiers) {
					boolean contains = false;
					for (Instruction instruction : frontier.phiFunctions) {
						PhiFunctionInstruction i = (PhiFunctionInstruction)instruction;
						if (i.destination == register) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						frontier.phiFunctions.add(PhiFunctionInstruction.getInstruction(register, frontier.predecessors));
						list.add(frontier);
					}
				}
			}
		}
	}

}
