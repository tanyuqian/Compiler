package Compiler.BackEnd.Allocator.GlobalRegisterAllocator.GraphColoring;

import Compiler.BackEnd.Allocator.GlobalRegisterAllocator.InterferenceGraph;
import Compiler.BackEnd.Allocator.PhysicalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GreedyGraphColoring extends GraphColoring {
	private Set<VirtualRegister> visit;

	public GreedyGraphColoring(InterferenceGraph graph) {
		super(graph);
		visit = new HashSet<>();
	}

	public Map<VirtualRegister, PhysicalRegister> analysis() {
		for (VirtualRegister vertex : graph.vertices) {
			if (!visit.contains(vertex)) {
				depthFirstSearch(vertex);
			}
		}
		return mapping;
	}

	private void depthFirstSearch(VirtualRegister vertex) {
		visit.add(vertex);
		color(vertex);
		for (VirtualRegister neighbor : graph.forbids.get(vertex)) {
			if (!visit.contains(neighbor)) {
				depthFirstSearch(neighbor);
			}
		}
	}
}
