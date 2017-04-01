package Compiler.BackEnd.Allocator.GlobalRegisterAllocator.GraphColoring;

import Compiler.BackEnd.Allocator.GlobalRegisterAllocator.InterferenceGraph;
import Compiler.BackEnd.Allocator.PhysicalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.*;

public class ChaitinGraphColoring extends GraphColoring {
	private Set<VirtualRegister> vertices;
	private Map<VirtualRegister, Integer> degree;

	public ChaitinGraphColoring(InterferenceGraph graph) {
		super(graph);
		vertices = new HashSet<>();
		degree = new HashMap<>();
	}

	public Map<VirtualRegister, PhysicalRegister> analysis() {
		for (VirtualRegister vertex : graph.vertices) {
			vertices.add(vertex);
			degree.put(vertex, graph.forbids.get(vertex).size());
		}
		Stack<VirtualRegister> stack = new Stack<>();
		while (stack.size() < graph.vertices.size()) {
			int origin = stack.size();
			for (VirtualRegister vertex : vertices) {
				if (degree.get(vertex) < InterferenceGraph.color.size()) {
					stack.add(vertex);
					remove(vertex);
					break;
				}
			}
			if (stack.size() != origin) {
				continue;
			}
			for (VirtualRegister vertex : vertices) {
				if (degree.get(vertex) >= InterferenceGraph.color.size()) {
					stack.add(vertex);
					remove(vertex);
					break;
				}
			}
		}
		while (!stack.isEmpty()) {
			color(stack.pop());
		}
		return mapping;
	}

	private void remove(VirtualRegister vertex) {
		vertices.remove(vertex);
		for (VirtualRegister neighbor : graph.forbids.get(vertex)) {
			degree.put(neighbor, degree.get(neighbor) - 1);
		}
	}
}
