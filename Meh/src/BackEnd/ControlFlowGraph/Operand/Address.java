package BackEnd.ControlFlowGraph.Operand;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Utility.CompilationError;

/**
 * Created by tan on 5/18/17.
 */
public class Address extends Operand {
    public VirtualRegister base;
    public ImmediateValue offset;
    public int size;

    public Address(VirtualRegister base, ImmediateValue offset, int size) {
        if (size != 8) {
            throw new CompilationError("Internal Error!");
        }
        this.base = base;
        this.offset = offset;
        this.size = size;
    }

    public Address(VirtualRegister base, int size) {
        if (size != 8) {
            throw new CompilationError("Internal Error!");
        }
        this.base = base;
        this.offset = new ImmediateValue(0);
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("address(%s + %s, %d)", base, offset, size);
    }
}
