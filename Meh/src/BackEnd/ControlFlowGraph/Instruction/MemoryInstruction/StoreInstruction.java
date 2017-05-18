package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.Operand;

/**
 * Created by tan on 5/18/17.
 */
public class StoreInstruction extends MemoryInstruction {
    public Operand operand;
    public Address address;

    public StoreInstruction(Operand operand, Address address) {
        this.operand = operand;
        this.address = address;
    }

    @Override
    public String toString() {
        return "store " + address.size + " " + address.base + " " + operand + " " + address.offset;
    }
}
