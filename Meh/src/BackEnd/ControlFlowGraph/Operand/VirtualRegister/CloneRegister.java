package BackEnd.ControlFlowGraph.Operand.VirtualRegister;

/**
 * Created by tan on 5/17/17.
 */
public class CloneRegister extends VirtualRegister {
    public VirtualRegister origin;
    public int version;

    public CloneRegister(VirtualRegister origin, int version) {
        this.origin = origin;
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("%s.%d", origin, version);
    }
}
