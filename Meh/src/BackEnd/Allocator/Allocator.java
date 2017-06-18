package BackEnd.Allocator;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import BackEnd.Translator.NASM.PhysicalRegistor;
import FrontEnd.AbstractSyntaxTree.Function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tan on 5/23/17.
 */
public class Allocator {
    public Function function;
    public Map<VirtualRegister, PhysicalRegistor> mapping;

    public Allocator(Function function) {
        this.function = function;
        this.mapping = new HashMap<>();
    }

    public Set<PhysicalRegistor> getUsedPhysicalRegister() {
        Set<PhysicalRegistor> result = new HashSet<>();
        for (VirtualRegister register : mapping.keySet()) {
            PhysicalRegistor physicalRegistor = mapping.get(register);
            result.add(physicalRegistor);
        }
        return result;
    }
}
