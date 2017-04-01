package Compiler.BackEnd.Optimizer;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.ControlFlowInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.ReturnInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.StoreInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.PhiFunctionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.CloneRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Pair;

import java.util.*;

public class UselessCodeEliminator {
	private Graph graph;

	public UselessCodeEliminator(Graph graph) {
		this.graph = graph.refresh();
	}

	public void process() {
		Map<VirtualRegister, Pair<Instruction, Block>> definition = new HashMap<>();
		for (Block block : graph.blocks) {
			for (Instruction instruction : block.phiFunctions) {
				for (VirtualRegister register : instruction.getDefinedRegisters()) {
					definition.put(register, new Pair<>(instruction, block));
				}
			}
			for (Instruction instruction : block.instructions) {
				for (VirtualRegister register : instruction.getDefinedRegisters()) {
					definition.put(register, new Pair<>(instruction, block));
				}
			}
		}
		Set<Instruction> live = new HashSet<>();
		Queue<Pair<Instruction, Block>> queue = new LinkedList<>();
		for (Block block : graph.blocks) {
			for (Instruction instruction : block.phiFunctions) {
				if (critical(instruction)) {
					live.add(instruction);
					queue.add(new Pair<>(instruction, block));
				}
			}
			for (Instruction instruction : block.instructions) {
				if (critical(instruction)) {
					live.add(instruction);
					queue.add(new Pair<>(instruction, block));
				}
			}
		}
		while (!queue.isEmpty()) {
			Instruction instruction = queue.peek().first;
			Block block = queue.peek().second;
			queue.poll();
			for (VirtualRegister register : instruction.getUsedRegisters()) {
				if (definition.containsKey(register)) {
					Pair<Instruction, Block> define = definition.get(register);
					if (!live.contains(define.first)) {
						live.add(define.first);
						queue.add(define);
					}
				}
			}
			if (instruction instanceof PhiFunctionInstruction) {
				PhiFunctionInstruction i = (PhiFunctionInstruction)instruction;
				for (Block predecessor : i.sources.keySet()) {
					Instruction j = predecessor.instructions.get(predecessor.instructions.size() - 1);
					if (j instanceof ControlFlowInstruction) {
						if (!live.contains(j)) {
							live.add(j);
							queue.add(new Pair<>(j, predecessor));
						}
					}
				}
			}
			for (Block frontier : block.postDominance.frontiers) {
				Instruction i = frontier.instructions.get(frontier.instructions.size() - 1);
				if (i instanceof ControlFlowInstruction) {
					if (!live.contains(i)) {
						live.add(i);
						queue.add(new Pair<>(i, frontier));
					}
				}
			}
		}
		for (Block block : graph.blocks) {
			for (int k = 0; k < block.phiFunctions.size(); ++k) {
				Instruction instruction = block.phiFunctions.get(k);
				if (!live.contains(instruction)) {
					block.phiFunctions.set(k, null);
				}
			}
			for (int k = 0; k < block.instructions.size(); ++k) {
				Instruction instruction = block.instructions.get(k);
				if (!live.contains(instruction)) {
					if (instruction instanceof ControlFlowInstruction) {
						if (instruction instanceof BranchInstruction) {
							Block nearest = block.postDominance.dominator;
							while (nearest != graph.exit) {
								boolean marked = false;
								for (Instruction i : nearest.phiFunctions) {
									if (live.contains(i)) {
										marked = true;
										break;
									}
								}
								for (Instruction i : nearest.instructions) {
									if (live.contains(i)) {
										marked = true;
										break;
									}
								}
								if (marked) {
									break;
								}
								nearest = nearest.postDominance.dominator;
							}
							block.instructions.set(k, JumpInstruction.getInstruction(nearest.label));
						}
					} else {
						block.instructions.set(k, null);
					}
				}
			}
		}
		graph.refresh();
	}

	private boolean critical(Instruction instruction) {
		if (instruction instanceof CallInstruction) {
			return true;
		}
		if (instruction instanceof ReturnInstruction) {
			return true;
		}
		if (instruction instanceof StoreInstruction) {
			return true;
		}
		for (VirtualRegister register : instruction.getDefinedRegisters()) {
			CloneRegister clone = (CloneRegister)register;
			if (clone.origin instanceof GlobalRegister) {
				return true;
			}
		}
		return false;
	}
}
