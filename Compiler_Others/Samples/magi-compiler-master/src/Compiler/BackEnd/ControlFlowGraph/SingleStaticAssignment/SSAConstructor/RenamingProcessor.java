package Compiler.BackEnd.ControlFlowGraph.SingleStaticAssignment.SSAConstructor;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.PhiFunctionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.CloneRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.SymbolTable.Symbol;

import java.util.*;

class RenamingProcessor {
	private Graph graph;
	private Map<VirtualRegister, Stack<VirtualRegister>> stack;

	RenamingProcessor(Graph graph) {
		this.graph = graph.refresh();
		this.renameRegisters();
	}

	private void renameRegisters() {
		stack = new HashMap<>();
		Set<VirtualRegister> registers = new HashSet<VirtualRegister>() {{
			for (Symbol parameter : graph.function.parameters) {
				add(parameter.register);
			}
			for (VirtualRegister register : graph.getAllRegisters()) {
				add(register);
			}
		}};
		for (VirtualRegister register : registers) {
			stack.put(register, new Stack<VirtualRegister>() {{
				add(null);
			}});
			if (!(register instanceof TemporaryRegister)) {
				clone(register);
			}
		}
		for (Symbol parameter : graph.function.parameters) {
			parameter.register = stack.get(parameter.register).peek();
		}
		rename(graph.enter);
	}

	private void rename(Block block) {
		for (Instruction instruction : block.phiFunctions) {
			for (VirtualRegister register : instruction.getDefinedRegisters()) {
				instruction.setDefinedRegister(register, clone(register));
			}
		}
		for (Instruction instruction : block.instructions) {
			for (VirtualRegister register : instruction.getUsedRegisters()) {
				if (stack.containsKey(register)) {
					instruction.setUsedRegister(register, stack.get(register).peek());
				}
			}
			for (VirtualRegister register : instruction.getDefinedRegisters()) {
				instruction.setDefinedRegister(register, clone(register));
			}
		}
		for (Block successor : block.successors) {
			for (Instruction instruction : successor.phiFunctions) {
				PhiFunctionInstruction i = (PhiFunctionInstruction)instruction;
				if (i.destination instanceof CloneRegister) {
					CloneRegister clone = (CloneRegister)i.destination;
					if (stack.containsKey(clone.origin)) {
						i.sources.put(block, stack.get(clone.origin).peek());
					}
				} else {
					if (stack.containsKey(i.destination)) {
						i.sources.put(block, stack.get(i.destination).peek());
					}
				}
			}
		}
		for (Block child : block.dominance.children) {
			rename(child);
		}
		for (Instruction instruction : block.phiFunctions) {
			for (VirtualRegister register : instruction.getDefinedRegisters()) {
				if (register instanceof CloneRegister) {
					CloneRegister clone = (CloneRegister)register;
					stack.get(clone.origin).pop();
				}
			}
		}
		for (Instruction instruction : block.instructions) {
			for (VirtualRegister register : instruction.getDefinedRegisters()) {
				if (register instanceof CloneRegister) {
					CloneRegister clone = (CloneRegister)register;
					stack.get(clone.origin).pop();
				}
			}
		}
	}

	private VirtualRegister clone(VirtualRegister register) {
		VirtualRegister clone = register.clone();
		stack.get(register).add(clone);
		return clone;
	}
}
