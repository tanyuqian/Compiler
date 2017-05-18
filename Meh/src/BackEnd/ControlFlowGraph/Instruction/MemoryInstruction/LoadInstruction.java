package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

/**
 * Created by tan on 5/18/17.
 */
public class LoadInstruction extends MemoryInstruction {
    public VirtualRegister destination;
    public Address address;

    public LoadInstruction(VirtualRegister destination, Address address) {
        this.destination = destination;
        this.address = address;
    }

    @Override
    public String toString() {
        return destination + " = load " + address.size + " " + address.base + " " + address.offset;
    }
}
