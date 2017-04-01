package Compiler.BackEnd.Allocator;

import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Allocator {
	public Function function;
	public Map<VirtualRegister, PhysicalRegister> mapping;

	protected Allocator(Function function) {
		this.function = function;
		this.mapping = new HashMap<>();
	}

	public Set<PhysicalRegister> getUsedPhysicalRegisters() {
		return new HashSet<PhysicalRegister>() {{
			for (VirtualRegister virtual : mapping.keySet()) {
				PhysicalRegister physical = mapping.get(virtual);
				if (physical != null) {
					add(physical);
				}
			}
		}};
	}
}
