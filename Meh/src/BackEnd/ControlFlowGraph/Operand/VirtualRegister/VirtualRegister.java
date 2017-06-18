package BackEnd.ControlFlowGraph.Operand.VirtualRegister;

import BackEnd.ControlFlowGraph.Operand.Operand;
import Environment.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/17/17.
 */
public abstract class VirtualRegister extends Operand {
    public int identity;
    public List<VirtualRegister> clones;

    public VirtualRegister() {
        this.identity = Environment.registerTable.registers.size();
        this.clones = new ArrayList<>();
    }

    public VirtualRegister clone() {
        VirtualRegister clone = new CloneRegister(this, clones.size());
        clones.add(clone);
        return clone;
    }

    @Override
    public String toString() {
        return String.format("$%d", identity);
    }
}
