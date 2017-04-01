package Compiler.BackEnd.Allocator.GlobalRegisterAllocator;

import Compiler.BackEnd.Allocator.Allocator;
import Compiler.BackEnd.Allocator.GlobalRegisterAllocator.GraphColoring.ChaitinGraphColoring;
import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;

import java.util.HashSet;
import java.util.Set;

public class GlobalRegisterAllocator extends Allocator {
	public GlobalRegisterAllocator(Function function) {
		super(function);
		function.graph.refresh();
		InterferenceGraph graph = new InterferenceGraph();
		for (Block block : function.graph.blocks) {
			for (Instruction instruction : block.instructions) {
				for (VirtualRegister register : instruction.getUsedRegisters()) {
					graph.add(register);
				}
				for (VirtualRegister register : instruction.getDefinedRegisters()) {
					graph.add(register);
				}
			}
		}
		for (Block block : function.graph.blocks) {
			Set<VirtualRegister> live = new HashSet<VirtualRegister>() {{
				block.liveliness.liveOut.forEach(this::add);
			}};
			for (int i = block.instructions.size() - 1; i >= 0; --i) {
				Instruction instruction = block.instructions.get(i);
				for (VirtualRegister register : instruction.getDefinedRegisters()) {
					for (VirtualRegister living : live) {
						graph.forbid(register, living);
					}
				}
				instruction.getDefinedRegisters().forEach(live::remove);
				instruction.getUsedRegisters().forEach(live::add);
			}
		}
		for (Block block : function.graph.blocks) {
			for (Instruction instruction : block.instructions) {
				if (instruction instanceof MoveInstruction) {
					MoveInstruction i = (MoveInstruction)instruction;
					if (i.source instanceof VirtualRegister) {
						graph.recommend(i.destination, (VirtualRegister)i.source);
					}
				}
			}
		}
		mapping = new ChaitinGraphColoring(graph).analysis();
	}
}
