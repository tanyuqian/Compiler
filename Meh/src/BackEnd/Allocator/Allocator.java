package BackEnd.Allocator;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import FrontEnd.AbstractSyntaxTree.Function;

import java.util.HashMap;
import java.util.Map;

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
}
