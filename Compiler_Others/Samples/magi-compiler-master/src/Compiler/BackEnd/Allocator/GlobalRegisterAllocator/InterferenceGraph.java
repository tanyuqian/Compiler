package Compiler.BackEnd.Allocator.GlobalRegisterAllocator;

import Compiler.BackEnd.Allocator.PhysicalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.BackEnd.Translator.MIPS.MIPSRegister;

import java.util.*;

public class InterferenceGraph {
	public static List<PhysicalRegister> color = new ArrayList<PhysicalRegister>() {{
		add(MIPSRegister.t0);
		add(MIPSRegister.t1);
		add(MIPSRegister.t2);
		add(MIPSRegister.t3);
		add(MIPSRegister.t4);
		add(MIPSRegister.t5);
		add(MIPSRegister.t6);
		add(MIPSRegister.t7);
		add(MIPSRegister.s0);
		add(MIPSRegister.s1);
		add(MIPSRegister.s2);
		add(MIPSRegister.s3);
		add(MIPSRegister.s4);
		add(MIPSRegister.s5);
		add(MIPSRegister.s6);
		add(MIPSRegister.s7);
		add(MIPSRegister.t8);
		add(MIPSRegister.t9);
		add(MIPSRegister.k0);
		add(MIPSRegister.k1);
		add(MIPSRegister.gp);
		add(MIPSRegister.fp);
	}};

	public Set<VirtualRegister> vertices;
	public Map<VirtualRegister, Set<VirtualRegister>> forbids;
	public Map<VirtualRegister, Set<VirtualRegister>> recommends;

	InterferenceGraph() {
		vertices = new HashSet<>();
		forbids = new HashMap<>();
		recommends = new HashMap<>();
	}

	void add(VirtualRegister x) {
		vertices.add(x);
		forbids.put(x, new HashSet<>());
		recommends.put(x, new HashSet<>());
	}

	void forbid(VirtualRegister x, VirtualRegister y) {
		if (x == y) {
			return;
		}
		if (x instanceof TemporaryRegister && y instanceof TemporaryRegister) {
			forbids.get(x).add(y);
			forbids.get(y).add(x);
		}
	}

	void recommend(VirtualRegister x, VirtualRegister y) {
		if (x == y) {
			return;
		}
		if (x instanceof TemporaryRegister && y instanceof TemporaryRegister) {
			recommends.get(x).add(y);
			recommends.get(y).add(x);
		}
	}
}