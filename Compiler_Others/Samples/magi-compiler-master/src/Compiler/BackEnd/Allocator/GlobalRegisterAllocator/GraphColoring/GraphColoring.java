package Compiler.BackEnd.Allocator.GlobalRegisterAllocator.GraphColoring;

import Compiler.BackEnd.Allocator.GlobalRegisterAllocator.InterferenceGraph;
import Compiler.BackEnd.Allocator.PhysicalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class GraphColoring {
	protected InterferenceGraph graph;
	Map<VirtualRegister, PhysicalRegister> mapping;

	GraphColoring(InterferenceGraph graph) {
		this.graph = graph;
		this.mapping = new HashMap<>();
	}

	void color(VirtualRegister vertex) {
		Set<PhysicalRegister> used = new HashSet<PhysicalRegister>() {{
			for (VirtualRegister neighbor : graph.forbids.get(vertex)) {
				if (mapping.containsKey(neighbor)) {
					add(mapping.get(neighbor));
				}
			}
			add(null);
		}};
		for (VirtualRegister neighbor : graph.recommends.get(vertex)) {
			if (!mapping.containsKey(neighbor)) {
				continue;
			}
			PhysicalRegister color = mapping.get(neighbor);
			if (!mapping.containsKey(vertex) && !used.contains(color)) {
				mapping.put(vertex, color);
				break;
			}
		}
		for (PhysicalRegister color : InterferenceGraph.color) {
			if (!mapping.containsKey(vertex) && !used.contains(color)) {
				mapping.put(vertex, color);
				break;
			}
		}
		if (!mapping.containsKey(vertex)) {
			mapping.put(vertex, null);
		}
	}
}
