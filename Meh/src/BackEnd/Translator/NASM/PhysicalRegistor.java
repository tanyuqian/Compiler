package BackEnd.Translator.NASM;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

/**
 * Created by tan on 5/23/17.
 */
public abstract class PhysicalRegistor {
    public int identity;
    public String name;

    public PhysicalRegistor(int identity, String name) {
        this.identity = identity;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
