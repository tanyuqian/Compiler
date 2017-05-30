package BackEnd.Allocator.RegisterAllocator;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import BackEnd.Translator.NASM.NASMRegister;
import BackEnd.Translator.NASM.PhysicalRegistor;

import java.util.*;

/**
 * Created by tan on 5/29/17.
 */
public class InterferenceGraph {
    public static List<PhysicalRegistor> colors = new ArrayList<PhysicalRegistor>() {{
        add(NASMRegister.rbx);
        add(NASMRegister.rsi);
        add(NASMRegister.rdi);
        add(NASMRegister.r8);
        add(NASMRegister.r9);
        add(NASMRegister.r12);
        add(NASMRegister.r13);
        add(NASMRegister.r14);
        add(NASMRegister.r15);
    }};

    public Set<VirtualRegister> vertices;
    public Map<VirtualRegister, Set<VirtualRegister>> forbids;
    public Map<VirtualRegister, Set<VirtualRegister>> recommend;

    public InterferenceGraph() {
        vertices = new HashSet<>();
        forbids = new HashMap<>();
        recommend = new HashMap<>();
    }

    void addVertice(VirtualRegister u) {
        vertices.add(u);
        forbids.put(u, new HashSet<>());
        recommend.put(u, new HashSet<>());
    }

    void addForbid(VirtualRegister u, VirtualRegister v) {
        if (u == v) {
            return ;
        }
        if (u instanceof TemporaryRegister && v instanceof TemporaryRegister) {
            forbids.get(u).add(v);
            forbids.get(v).add(u);
        }
    }

    void addRecommend(VirtualRegister u, VirtualRegister v) {
        if (u == v) {
            return ;
        }
        if (u instanceof TemporaryRegister && v instanceof TemporaryRegister) {
            recommend.get(u).add(v);
            recommend.get(v).add(u);
        }
    }
}
