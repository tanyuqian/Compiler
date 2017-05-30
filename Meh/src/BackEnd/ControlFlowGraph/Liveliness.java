package BackEnd.ControlFlowGraph;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tan on 5/29/17.
 */
public class Liveliness {
    public List<VirtualRegister> defined, used;
    public Set<VirtualRegister> liveIn, liveOut;

    public Liveliness() {
        defined = new ArrayList<>();
        used = new ArrayList<>();
        liveIn = new HashSet<>();
        liveOut = new HashSet<>();
    }
}
