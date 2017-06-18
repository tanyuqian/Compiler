package Compiler.BackEnd.Optimizer;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.ControlFlowInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.ReturnInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Utility.Error.InternalError;

public class ControlFlowOptimizer {
	private Graph graph;

	public ControlFlowOptimizer(Graph graph) {
		this.graph = graph.refresh();
	}

	public void process() {
		while (true) {
			boolean modified = false;
			for (Block block : graph.blocks) {
				if (block == graph.enter || block == graph.entry || block == graph.exit) {
					continue;
				}
				if (mergeBlocks(block)) {
					modified = true;
					break;
				}
				if (eliminateJumps(block)) {
					modified = true;
					break;
				}
			}
			if (eliminateDeadCode()) {
				modified = true;
			}
			if (modified) {
				graph.refresh();
			} else {
				break;
			}
		}
	}

	private boolean mergeBlocks(Block block) {
		if (block.successors.size() == 1) {
			Block successor = block.successors.get(0);
			if (successor == graph.enter || successor == graph.entry || successor == graph.exit) {
				return false;
			}
			if (successor.predecessors.size() == 1) {
				JumpInstruction a = (JumpInstruction)block.instructions.remove(block.instructions.size() - 1);
				for (Block predecessor : block.predecessors) {
					Instruction i = predecessor.instructions.get(predecessor.instructions.size() - 1);
					if (i instanceof JumpInstruction) {
						JumpInstruction b = (JumpInstruction)i;
						b.to = a.to;
					} else if (i instanceof BranchInstruction) {
						BranchInstruction b = (BranchInstruction)i;
						if (b.trueTo.block == block) {
							b.trueTo = a.to;
						}
						if (b.falseTo.block == block) {
							b.falseTo = a.to;
						}
					} else {
						throw new InternalError();
					}
				}
				block.instructions.addAll(successor.instructions);
				successor.instructions = block.instructions;
				graph.blocks.remove(block);
				return true;
			}
		}
		return false;
	}

	private boolean eliminateJumps(Block block) {
		Instruction instruction = block.instructions.get(block.instructions.size() - 1);
		if (instruction instanceof JumpInstruction) {
			JumpInstruction a = (JumpInstruction)instruction;
			Block successor = block.successors.get(0);
			if (successor.instructions.size() == 1) {
				if (successor.instructions.get(0) instanceof JumpInstruction) {
					JumpInstruction b = (JumpInstruction)successor.instructions.get(0);
					a.to = b.to;
					return true;
				}
			}
		}
		if (instruction instanceof BranchInstruction) {
			BranchInstruction a = (BranchInstruction)instruction;
			{
				Block successor = block.successors.get(0);
				if (successor.instructions.size() == 1) {
					if (successor.instructions.get(0) instanceof JumpInstruction) {
						JumpInstruction b = (JumpInstruction)successor.instructions.get(0);
						a.trueTo = b.to;
						return true;
					}
				}
			}
			{
				Block successor = block.successors.get(1);
				if (successor.instructions.size() == 1) {
					if (successor.instructions.get(0) instanceof JumpInstruction) {
						JumpInstruction b = (JumpInstruction)successor.instructions.get(0);
						a.falseTo = b.to;
						return true;
					}
				}
			}
		}
		if (block.instructions.size() == 2) {
			//	optimize the short circuit evaluation
			if (block.instructions.get(0) instanceof MoveInstruction && block.instructions.get(1) instanceof JumpInstruction) {
				MoveInstruction a = (MoveInstruction)block.instructions.get(0);
				JumpInstruction b = (JumpInstruction)block.instructions.get(1);
				if (a.source instanceof ImmediateValue) {
					Block successor = block.successors.get(0);
					if (successor.instructions.size() == 1) {
						if (successor.instructions.get(0) instanceof BranchInstruction) {
							BranchInstruction c = (BranchInstruction)successor.instructions.get(0);
							if (c.condition == a.destination) {
								int literal = ((ImmediateValue)a.source).literal;
								if (literal == 0) {
									b.to = c.falseTo;
								} else {
									b.to = c.trueTo;
								}
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean eliminateDeadCode() {
		boolean modified = false;
		for (Block block : graph.blocks) {
			if (block == graph.enter || block == graph.entry || block == graph.exit) {
				continue;
			}
			for (int k = block.instructions.size() - 1; k >= 0; --k) {
				Instruction instruction = block.instructions.get(k);
				if (instruction instanceof ControlFlowInstruction) {
					continue;
				}
				if (instruction instanceof CallInstruction) {
					continue;
				}
				if (instruction instanceof ReturnInstruction) {
					continue;
				}
				if (instruction.getDefinedRegisters().isEmpty()) {
					continue;
				}
				boolean useless = true;
				for (VirtualRegister defined : instruction.getDefinedRegisters()) {
					if (!(defined instanceof TemporaryRegister)) {
						useless = false;
						break;
					}
					for (int j = k + 1; j < block.instructions.size(); ++j) {
						if (block.instructions.get(j) == null) {
							continue;
						}
						for (VirtualRegister used : block.instructions.get(j).getUsedRegisters()) {
							if (used.equals(defined)) {
								useless = false;
								break;
							}
						}
					}
					if (block.liveliness.liveOut.contains(defined)) {
						useless = false;
						break;
					}
				}
				if (instruction instanceof MoveInstruction) {
					MoveInstruction i = (MoveInstruction)instruction;
					if (i.destination == i.source) {
						useless = true;
					}
				}
				if (useless) {
					block.instructions.set(k, null);
					modified = true;
				}
			}
		}
		return modified;
	}
}
