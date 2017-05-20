package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.Operand;
import Utility.CompilationError;

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

    public static Instruction getInstruction(Operand operand, Operand address) {
        if (address instanceof Address) {
            return new StoreInstruction(operand, (Address)address);
        }
        throw new CompilationError("Internal Error!");
    }

    @Override
    public String toString() {
        return "store " + address.size + " " + address.base + " " + operand + " " + address.offset;
    }
}
