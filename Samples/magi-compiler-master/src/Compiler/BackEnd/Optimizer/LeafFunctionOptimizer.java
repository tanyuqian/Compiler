package Compiler.BackEnd.Optimizer;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.ParameterRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.Environment;
import Compiler.Utility.Error.InternalError;

import java.util.HashMap;
import java.util.Map;

public class LeafFunctionOptimizer {
	private Graph graph;

	public LeafFunctionOptimizer(Graph graph) {
		this.graph = graph.refresh();
	}

	public void process() {
		for (Block block : graph.blocks) {
			for (Instruction instruction : block.instructions) {
				if (instruction instanceof CallInstruction) {
					CallInstruction i = (CallInstruction)instruction;
					if (!i.function.name.startsWith("____builtin")) {
						return;
					}
				}
			}
		}
		optimizeVariables();
	}

	private void optimizeVariables() {
		Map<VirtualRegister, VirtualRegister> mapping = new HashMap<VirtualRegister, VirtualRegister>() {{
			for (Block block : graph.blocks) {
				if (block == graph.enter || block == graph.entry || block == graph.exit) {
					continue;
				}
				for (Instruction instruction : block.instructions) {
					for (VirtualRegister register : instruction.getUsedRegisters()) {
						if (register instanceof GlobalRegister || register instanceof ParameterRegister) {
							put(register, null);
						}
					}
					for (VirtualRegister register : instruction.getDefinedRegisters()) {
						if (register instanceof GlobalRegister || register instanceof ParameterRegister) {
							put(register, null);
						}
					}
				}
			}
		}};
		for (VirtualRegister variable : mapping.keySet()) {
			mapping.put(variable, Environment.registerTable.addTemporaryRegister());
		}
		for (Block block : graph.blocks) {
			if (block == graph.enter || block == graph.exit) {
				continue;
			}
			for (Instruction instruction : block.instructions) {
				for (VirtualRegister register : instruction.getDefinedRegisters()) {
					if (mapping.containsKey(register)) {
						instruction.setDefinedRegister(register, mapping.get(register));
					}
				}
				for (VirtualRegister register : instruction.getUsedRegisters()) {
					if (mapping.containsKey(register)) {
						instruction.setUsedRegister(register, mapping.get(register));
					}
				}
			}
		}
		Instruction i = graph.enter.instructions.remove(graph.enter.instructions.size() - 1);
		if (!(i instanceof JumpInstruction)) {
			throw new InternalError();
		}
		for (VirtualRegister variable : mapping.keySet()) {
			VirtualRegister temporary = mapping.get(variable);
			graph.enter.instructions.add(MoveInstruction.getInstruction(temporary, variable));
		}
		graph.enter.instructions.add(i);
		for (VirtualRegister variable : mapping.keySet()) {
			VirtualRegister temporary = mapping.get(variable);
			graph.exit.instructions.add(MoveInstruction.getInstruction(variable, temporary));
		}
	}
}
