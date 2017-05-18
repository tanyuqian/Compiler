package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

/**
 * Created by tan on 5/18/17.
 */
public class AllocateInstruction extends MemoryInstruction {
    public VirtualRegister destination;
    public Operand size;

    public AllocateInstruction(VirtualRegister destination, Operand size) {
        this.destination = destination;
        this.size = size;
    }

    @Override
    public String toString() {
        return destination + " = alloc " + size;
    }
}
